package nl.mentaalachtergesteld.drones.entity.client;

import net.minecraft.resources.ResourceLocation;
import nl.mentaalachtergesteld.drones.Drones;
import nl.mentaalachtergesteld.drones.entity.custom.DroneEntity;
import software.bernie.geckolib.model.GeoModel;

public class DroneModel extends GeoModel<DroneEntity> {
    @Override
    public ResourceLocation getModelResource(DroneEntity animatable) {
        return new ResourceLocation(Drones.ID, "geo/drone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DroneEntity animatable) {
        return new ResourceLocation(Drones.ID, "textures/entity/drone.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DroneEntity animatable) {
        return new ResourceLocation(Drones.ID, "animations/drone.animation.json");
    }
}
