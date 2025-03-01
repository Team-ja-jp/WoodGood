package net.mehvahdjukaar.every_compat.modules.mcaw;

import com.mcwpaths.kikoz.MacawsPaths;
import com.mcwpaths.kikoz.init.BlockInit;
import com.mcwpaths.kikoz.objects.FacingPathBlock;
import net.mehvahdjukaar.every_compat.api.SimpleEntrySet;
import net.mehvahdjukaar.every_compat.api.SimpleModule;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodType;
import net.mehvahdjukaar.moonlight.api.set.wood.WoodTypeRegistry;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;


public class MacawPathsModule extends SimpleModule {

    public final SimpleEntrySet<WoodType, Block> PLANKS_PATHS;

    public MacawPathsModule(String modId) {
        super(modId, "mcp");
        CreativeModeTab tab = MacawsPaths.PathsItemGroup;


        PLANKS_PATHS = SimpleEntrySet.builder(WoodType.class, "planks_path",
                        BlockInit.OAK_PLANKS_PATH, () -> WoodTypeRegistry.OAK_TYPE,
                        w -> new FacingPathBlock(Utils.copyPropertySafe(w.planks).strength(1.5f, 2.3f)))
                .addTag(BlockTags.MINEABLE_WITH_AXE, Registry.BLOCK_REGISTRY)
                .setTab(() -> tab)
                .defaultRecipe()
                .setRenderType(() -> RenderType::cutout)
                .addTexture(modRes("block/oak_planks_path"))
                .build();

        this.addEntry(PLANKS_PATHS);
    }
}
