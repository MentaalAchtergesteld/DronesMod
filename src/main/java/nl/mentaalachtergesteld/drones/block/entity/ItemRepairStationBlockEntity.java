package nl.mentaalachtergesteld.drones.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.block.custom.ItemRepairStation;
import nl.mentaalachtergesteld.drones.networking.ModMessages;
import nl.mentaalachtergesteld.drones.networking.packet.ItemStackSyncS2CPacket;
import nl.mentaalachtergesteld.drones.screen.ItemRepairStationMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ItemRepairStationBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public void setStackInSlot(int slot, @NotNull ItemStack stack) {
            super.setStackInSlot(slot, stack);
            this.onContentsChanged(slot);
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
         return switch (slot) {
             case 0 -> stack.getItem() == Items.REDSTONE;
             case 1 -> stack.isDamageableItem();
             default -> super.isItemValid(slot, stack);
         };
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(
                    Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> false)),
                    Direction.UP, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1, (i, s) -> itemHandler.isItemValid(1, s))),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> itemHandler.isItemValid(0, s))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0, (i, s) -> itemHandler.isItemValid(0, s))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 0 || i == 1, (i, s) -> itemHandler.isItemValid(0, s) || itemHandler.isItemValid(1, s)))
            );

    protected final ContainerData data;
    private int fuel = 0;
    private int maxFuel = 100;

    public ItemRepairStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ITEM_REPAIR_STATION.get(), blockPos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ItemRepairStationBlockEntity.this.fuel;
                    case 1 -> ItemRepairStationBlockEntity.this.maxFuel;
                    default ->  0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ItemRepairStationBlockEntity.this.fuel = pValue;
                    case 1 -> ItemRepairStationBlockEntity.this.maxFuel = pValue;
                };
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("container.drones.item_repair_station");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ItemRepairStationMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(ItemRepairStation.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private boolean hasFuel() {
        if(this.fuel > 0) return true;
        if(!this.itemHandler.getStackInSlot(1).isRepairable() || this.itemHandler.getStackInSlot(0).getItem() != Items.REDSTONE || !this.itemHandler.getStackInSlot(1).isDamaged()) return false;

        this.itemHandler.extractItem(0, 1, false);
        this.fuel = 100;
        return true;
    }

    private static void repairItem(ItemRepairStationBlockEntity pEntity) {
        ItemStack itemStack = pEntity.itemHandler.getStackInSlot(1);
        if(itemStack.getDamageValue() > 0) {
            itemStack.setDamageValue(itemStack.getDamageValue()-1);
        }
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ItemRepairStationBlockEntity pEntity) {
        if(!level.isClientSide()) {
            if(pEntity.hasFuel()) {
                pEntity.fuel--;
                setChanged(level, blockPos, blockState);

                repairItem(pEntity);
            }
        }
    }

    public ItemStack getRenderStack() {
        ItemStack stack;
        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = new ItemStack(Items.AIR);
        }
        return stack;
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }


    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public int getAnalogOutputSignal() {
        if (!itemHandler.getStackInSlot(1).isEmpty()) {
            ItemStack item = itemHandler.getStackInSlot(1);
            Drones.LOGGER.info("MAX DAMAGE: " + item.getMaxDamage());
            Drones.LOGGER.info("DAMAGE VALUE: " + item.getDamageValue());
            Drones.LOGGER.info("FORMULA: " + 14.0/item.getMaxDamage()*item.getDamageValue()+1);

            if(item.getDamageValue() == 0) {
                return 0;
            }

            return (int) Math.floor(14.0/item.getMaxDamage()*item.getDamageValue()+1);

        }
        return 0;
    }
}

