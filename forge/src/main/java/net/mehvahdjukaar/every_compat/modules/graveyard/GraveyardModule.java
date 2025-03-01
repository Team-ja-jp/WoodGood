
package net.mehvahdjukaar.every_compat.modules.graveyard;

import com.finallion.graveyard.TheGraveyard;
import com.finallion.graveyard.blockentities.SarcophagusBlockEntity;
import com.finallion.graveyard.blockentities.render.SarcophagusBlockEntityRenderer;
import com.finallion.graveyard.blocks.SarcophagusBlock;
import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.ClientPlatformHelper;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class GraveyardModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> COFFINS;

    public static BlockEntityType<? extends SarcophagusBlockEntity> COFFIN_TILE;

    public GraveyardModule(String modId) {
        super(modId, "gy");

        COFFINS = SimpleEntrySet.builder(WoodType.class, "coffin",
                        () -> getModBlock("oak_coffin"), () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new CompatCoffinfBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion().strength(1.0F), w))
                .addTag(modRes("coffins"), Registry.BLOCK_REGISTRY)
                .addTag(modRes("coffins"), Registry.ITEM_REGISTRY)
                .defaultRecipe()
                .setTab(() -> TheGraveyard.GROUP)
                .addTile(CompatCoffinBlockTile::new)
                .addTextureM(modRes("block/oak_coffin"), EveryCompat.res("model/oak_coffin_m"))

                .build();

        this.addEntry(COFFINS);

    }

    @Override
    public void registerBlockEntityRenderers(ClientPlatformHelper.BlockEntityRendererEvent event) {
        event.register((BlockEntityType<CompatCoffinBlockTile>) (COFFINS.getTileHolder().tile), CompatCoffinRenderer::new);
    }

    @Override
    public void registerTiles(Registrator<BlockEntityType<?>> registry) {
        super.registerTiles(registry);
        COFFIN_TILE = (BlockEntityType<? extends SarcophagusBlockEntity>) COFFINS.getTileHolder().tile;
    }

    //idk why but object holder class loader thingie keeps trying to load this if its not inner private like this
    class CompatCoffinBlockTile extends SarcophagusBlockEntity {

        private final WoodType woodType;

        public CompatCoffinBlockTile(BlockPos pos, BlockState state) {
            super(pos, state);
            this.woodType = ((CompatCoffinfBlock) state.getBlock()).getWoodType();
        }

        @Override
        public BlockEntityType<?> getType() {
            return COFFINS.getTileHolder().tile;
        }

        public WoodType getWoodType() {
            return this.woodType;
        }
    }

    private class CompatCoffinfBlock extends SarcophagusBlock {
        private final WoodType woodType;

        public CompatCoffinfBlock(Properties properties, WoodType woodType) {
            super(properties, true, "lid", "base");
            this.woodType = woodType;
        }

        public WoodType getWoodType() {
            return woodType;
        }

        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return new CompatCoffinBlockTile(pos, state);
        }
    }
}