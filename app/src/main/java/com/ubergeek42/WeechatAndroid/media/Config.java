package com.ubergeek42.WeechatAndroid.media;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class Config {
    enum Secure {
        OPTIONAL,
        REQUIRED,
        REWRITE
    }

    static Secure secure = Secure.REWRITE;

    enum Enable {
        NEVER,
        WIFI_ONLY,
        UNMETERED_ONLY,
        ALWAYS
    }

    static Enable enabled = Enable.UNMETERED_ONLY;

    @Nullable final static public Pattern LINKIFY_MESSAGE_FILTER = Pattern.compile("^<[^ ]{1,16} \".{1,33}\"> ", Pattern.CASE_INSENSITIVE);

    @SuppressWarnings("SpellCheckingInspection")
    static void setup() {
        Engine.setLineFilters(Arrays.asList(
                new LineFilter(Collections.singletonList("tacgnol"), null),
                new LineFilter(null, "^(?:Title: |↑ )")
        ));

        Engine.registerStrategy(Arrays.asList(
                new StrategyRegex(
                        "youtube",
                        Arrays.asList("www.youtube.com", "m.youtube.com", "youtube.com", "youtu.be"),
                        "https?://" +
                                "(?:" +
                                "(?:www\\.|m\\.)?youtube\\.com/watch\\?v=" +
                                "|" +
                                "youtu\\.be/" +
                                ")" +
                                "([A-Za-z0-9_-]+).*",
                        "https://img.youtube.com/vi/$1/mqdefault.jpg",
                        "https://img.youtube.com/vi/$1/hqdefault.jpg"),
                new StrategyRegex(
                        "i.imgur.com",
                        Collections.singletonList("i.imgur.com"),
                        "https?://i.imgur.com/([^.]+).*",
                        "https://i.imgur.com/$1m.jpg",
                        "https://i.imgur.com/$1h.jpg"),
                new StrategyRegex(
                        "9gag.com",
                        Collections.singletonList("9gag.com"),
                        "https?://9gag\\.com/gag/([^_]+)",
                        "https://images-cdn.9gag.com/photo/$1_700b.jpg",
                        "https://images-cdn.9gag.com/photo/$1_700b.jpg"),
                new StrategyRegex(
                        "9cache.com",
                        Collections.singletonList("img-9gag-fun.9cache.com"),
                        "https?://img-9gag-fun\\.9cache\\.com/photo/([^_]+).*",
                        "https://images-cdn.9gag.com/photo/$1_700b.jpg",
                        "https://images-cdn.9gag.com/photo/$1_700b.jpg"),
                new StrategyAny(
                        "pikabu.ru",
                        Arrays.asList("pikabu.ru", "www.pikabu.ru"),
                        "https://pikabu.ru/story/.+",
                        null, 4096),
                new StrategyAny(
                        "common→https",
                        Arrays.asList("*.wikipedia.org", "gfycat.com", "imgur.com"),
                        "https?://(.+)",
                        "https://$1",
                        4096 * 2),
                new StrategyAny(
                        "reddit",
                        Arrays.asList("v.redd.it", "reddit.com", "www.reddit.com", "old.reddit.com"),
                        null, null, 131072),
                new StrategyAny(
                        "any",
                        Collections.singletonList("*"),
                        null, null, 4096 * 2 * 2),
                new StrategyNull(
                        Arrays.asList("pastebin.com", "github.com", "bpaste.net", "dpaste.com"))
        ));
    }
}
