package nl.mentaalachtergesteld.drones.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import nl.mentaalachtergesteld.drones.block.entity.ModBlockEntities;
import nl.mentaalachtergesteld.drones.block.entity.ItemRepairStationBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ItemRepairStation extends BaseEntityBlock {
    public ItemRepairStation(Properties properties) {
        super(properties);
    }

    private static final VoxelShape SHAPE =
        Block.box(0.0,0.0,0.0,16.0,14.0,16.0);

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newBlockState, boolean isMoving) {
        if(blockState.getBlock() != newBlockState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if(blockEntity instanceof ItemRepairStationBlockEntity) {
                ((ItemRepairStationBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(blockState, level, blockPos, newBlockState, isMoving);
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {

        if(level.isClientSide()) {
            return super.use(blockState, level, blockPos, player, interactionHand, blockHitResult);
        }

        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if(blockEntity instanceof ItemRepairStationBlockEntity) {
            NetworkHooks.openScreen(((ServerPlayer) player), (ItemRepairStationBlockEntity)blockEntity, blockPos);
        } else {
            throw new IllegalStateException("Our container provider is missing!");
        }


        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ItemRepairStationBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlockEntities.ITEM_REPAIR_STATION.get(), ItemRepairStationBlockEntity::tick);
    }
}
