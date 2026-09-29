package net.derppickles.superstructure.attachment;

import com.mojang.serialization.Codec;
import net.derppickles.superstructure.Superstructure;
import net.derppickles.superstructure.attachment.handler.DesertCurseHandler;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

public class ModAttachmentTypes {
    public static final AttachmentType<Boolean> DESERT_CURSE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(Superstructure.MOD_ID, "desert_curse"),
            builder -> builder
                    .initializer(() -> false)
                    .persistent(Codec.BOOL)
                    .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.targetOnly())
    );

    public static void registerModAttachments() {
        DesertCurseHandler.register();
        Superstructure.LOGGER.info("Registering Mod Attachments for " + Superstructure.MOD_ID);
    }
}
