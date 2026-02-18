package io.github.scuba10steve.s3.network;

import io.github.scuba10steve.s3.EZStorage;
import io.github.scuba10steve.s3.blockentity.ExtractPortBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

/**
 * Packet sent from client to server to configure the Extract Port.
 */
public record ExtractPortConfigPacket(BlockPos pos, boolean cycleMode, boolean roundRobin) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ExtractPortConfigPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(EZStorage.MODID, "extract_port_config"));

    public static final StreamCodec<FriendlyByteBuf, ExtractPortConfigPacket> STREAM_CODEC = StreamCodec.of(
        (buf, packet) -> {
            buf.writeBlockPos(packet.pos);
            buf.writeBoolean(packet.cycleMode);
            buf.writeBoolean(packet.roundRobin);
        },
        buf -> new ExtractPortConfigPacket(
            buf.readBlockPos(),
            buf.readBoolean(),
            buf.readBoolean()
        )
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.level().getBlockEntity(pos) instanceof ExtractPortBlockEntity extractPort) {
                if (cycleMode) {
                    extractPort.cycleListMode();
                } else {
                    extractPort.setRoundRobin(roundRobin);
                }
            }
        });
    }
}
