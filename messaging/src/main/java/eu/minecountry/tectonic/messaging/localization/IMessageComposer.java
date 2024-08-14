package eu.minecountry.tectonic.messaging.localization;

import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.List;

public interface IMessageComposer {

    String build();

    List<TagResolver> replacements();

}
