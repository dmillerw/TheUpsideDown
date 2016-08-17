package me.dmillerw.upsidedown.asm.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * Created by dmillerw
 */
public class GetBlockModelEvent extends Event {

    private final BlockModelShapes blockModelShapes;
    private final IBlockState blockState;
    private IBakedModel blockModel;

    public GetBlockModelEvent(BlockModelShapes blockModelShapes, IBlockState blockState, IBakedModel blockModel) {
        this.blockModelShapes = blockModelShapes;
        this.blockState = blockState;
        this.blockModel = blockModel;
    }

    public BlockModelShapes getBlockModelShapes() {
        return blockModelShapes;
    }

    public IBlockState getBlockState() {
        return blockState;
    }

    public IBakedModel getBlockModel() {
        return blockModel;
    }

    public void setBlockModel(IBakedModel blockModel) {
        this.blockModel = blockModel;
    }
}
