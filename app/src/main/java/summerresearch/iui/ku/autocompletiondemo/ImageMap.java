package summerresearch.iui.ku.autocompletiondemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ElifYagmur on 25.07.2016.
 * ImageMap keeps bitmap of classes' image that exists in the data set.
 */
public class ImageMap {
    private Map <String, Bitmap> imageMap;
    Context c;

    public ImageMap(Context c) {
        this.c = c;
        imageMap = new HashMap<String, Bitmap>() {{
            put("airplane", getBitMap(R.drawable.airplane));
            put("alarm-clock", getBitMap(R.drawable.alarmclock));
            put("angel", getBitMap(R.drawable.angel));
            put("ant", getBitMap(R.drawable.ant));
            put("apple", getBitMap(R.drawable.apple));

            put("arm", getBitMap(R.drawable.arm));
            put("armchair", getBitMap(R.drawable.armchair));
            put("ashtray", getBitMap(R.drawable.ashtray));
            put("axe", getBitMap(R.drawable.axe));
            put("backpack", getBitMap(R.drawable.backpack));

            put("banana", getBitMap(R.drawable.banana));
            put("barn", getBitMap(R.drawable.barn));
            put("baseball-bat", getBitMap(R.drawable.baseballbat));
            put("basket", getBitMap(R.drawable.basket));
            put("bathtub", getBitMap(R.drawable.bathtub));

            put("bear-(animal)", getBitMap(R.drawable.bear));
            put("bed", getBitMap(R.drawable.bed));
            put("bee", getBitMap(R.drawable.bee));
            put("beer-mug", getBitMap(R.drawable.beermug));
            put("bell", getBitMap(R.drawable.bell));

            put("bench", getBitMap(R.drawable.bench));
            put("bicycle", getBitMap(R.drawable.bicycle));
            put("binoculars", getBitMap(R.drawable.binoculars));
            put("blimp", getBitMap(R.drawable.blimp));
            put("book", getBitMap(R.drawable.book));

            put("bookshelf", getBitMap(R.drawable.bookshelf));
            put("boomerang", getBitMap(R.drawable.boomerang));
            put("bottle-opener", getBitMap(R.drawable.bottleopener));
            put("bowl", getBitMap(R.drawable.bowl));
            put("brain", getBitMap(R.drawable.brain));

            put("bread", getBitMap(R.drawable.bread));
            put("bridge", getBitMap(R.drawable.bridge));
            put("bulldozer", getBitMap(R.drawable.bulldozer));
            put("bus", getBitMap(R.drawable.bus));
            put("bush", getBitMap(R.drawable.bush));

            put("butterfly", getBitMap(R.drawable.butterfly));
            put("cabinet", getBitMap(R.drawable.cabinet));
            put("cactus", getBitMap(R.drawable.cactus));
            put("cake", getBitMap(R.drawable.cake));
            put("calculator", getBitMap(R.drawable.calculator));

            put("camel", getBitMap(R.drawable.camel));
            put("camera", getBitMap(R.drawable.camera));
            put("candle", getBitMap(R.drawable.candle));
            put("cannon", getBitMap(R.drawable.cannon));
            put("canoe", getBitMap(R.drawable.canoe));

            put("car-(sedan)", getBitMap(R.drawable.car));
            put("carrot", getBitMap(R.drawable.carrot));
            put("castle", getBitMap(R.drawable.castle));
            put("cat", getBitMap(R.drawable.cat));
            put("cell-phone", getBitMap(R.drawable.cellphone));

            put("chair", getBitMap(R.drawable.chair));
            put("chandelier", getBitMap(R.drawable.chandelier));
            put("church", getBitMap(R.drawable.church));
            put("cigarette", getBitMap(R.drawable.cigarette));
            put("cloud", getBitMap(R.drawable.cloud));

            put("comb", getBitMap(R.drawable.comb));
            put("computer-monitor", getBitMap(R.drawable.computermonitor));
            put("computer-mouse", getBitMap(R.drawable.computermouse));
            put("couch", getBitMap(R.drawable.couch));
            put("cow", getBitMap(R.drawable.cow));

            put("crab", getBitMap(R.drawable.crab));
            put("crane-(machine)", getBitMap(R.drawable.crane));
            put("crocodile", getBitMap(R.drawable.crocodile));
            put("crown", getBitMap(R.drawable.crown));
            put("cup", getBitMap(R.drawable.cup));

            put("diamond", getBitMap(R.drawable.diamond));
            put("dog", getBitMap(R.drawable.dog));
            put("dolphin", getBitMap(R.drawable.dolphin));
            put("donut", getBitMap(R.drawable.donut));
            put("door", getBitMap(R.drawable.door));

            put("door-handle", getBitMap(R.drawable.doorhandle));
            put("dragon", getBitMap(R.drawable.dragon));
            put("duck", getBitMap(R.drawable.duck));
            put("ear", getBitMap(R.drawable.ear));
            put("elephant", getBitMap(R.drawable.elephant));

            put("envelope", getBitMap(R.drawable.envelope));
            put("eye", getBitMap(R.drawable.eye));
            put("eyeglasses", getBitMap(R.drawable.eyeglasses));
            put("face", getBitMap(R.drawable.face));
            put("fan", getBitMap(R.drawable.fan));

            put("feather", getBitMap(R.drawable.feather));
            put("fire-hydrant", getBitMap(R.drawable.firehydrant));
            put("fish", getBitMap(R.drawable.fish));
            put("flashlight", getBitMap(R.drawable.flashlight));
            put("floor-lamp", getBitMap(R.drawable.floorlamp));

            put("flower-with-stem", getBitMap(R.drawable.flowerwithstem));
            put("flying-bird", getBitMap(R.drawable.flyingbird));
            put("flying-saucer", getBitMap(R.drawable.flyingsaucer));
            put("foot", getBitMap(R.drawable.foot));
            put("fork", getBitMap(R.drawable.fork));

            put("frog", getBitMap(R.drawable.forg));
            put("frying-pan", getBitMap(R.drawable.fryingpan));
            put("giraffe", getBitMap(R.drawable.giraffe));
            put("grapes", getBitMap(R.drawable.grapes));
            put("grenade", getBitMap(R.drawable.grenade));

            put("guitar", getBitMap(R.drawable.guitar));
            put("hamburger", getBitMap(R.drawable.hamburger));
            put("hammer", getBitMap(R.drawable.hammer));
            put("hand", getBitMap(R.drawable.hand));
            put("harp", getBitMap(R.drawable.harp));

            put("hat", getBitMap(R.drawable.hat));
            put("head", getBitMap(R.drawable.head));
            put("head-phones", getBitMap(R.drawable.headphones));
            put("hedgehog", getBitMap(R.drawable.hedgehog));
            put("helicopter", getBitMap(R.drawable.helicopter));

            put("helmet", getBitMap(R.drawable.helmet));
            put("horse", getBitMap(R.drawable.horse));
            put("hot-air-balloon", getBitMap(R.drawable.hotairballoon));
            put("hot-dog", getBitMap(R.drawable.hotdog));
            put("hourglass", getBitMap(R.drawable.hourglass));

            put("house", getBitMap(R.drawable.house));
            put("human-skeleton", getBitMap(R.drawable.humanskeleton));
            put("ice-cream-cone", getBitMap(R.drawable.icecreamcone));
            put("ipod", getBitMap(R.drawable.ipod));
            put("kangaroo", getBitMap(R.drawable.kangaroo));

            put("key", getBitMap(R.drawable.key));
            put("keyboard", getBitMap(R.drawable.keyboard));
            put("knife", getBitMap(R.drawable.knife));
            put("ladder", getBitMap(R.drawable.ladder));
            put("laptop", getBitMap(R.drawable.laptop));

            put("leaf", getBitMap(R.drawable.leaf));
            put("lightbulb", getBitMap(R.drawable.lightbulb));
            put("lighter", getBitMap(R.drawable.lighter));
            put("lion", getBitMap(R.drawable.lion));
            put("lobster", getBitMap(R.drawable.lobster));

            put("loudspeaker", getBitMap(R.drawable.loudspeaker));
            put("mailbox", getBitMap(R.drawable.mailbox));
            put("megaphone", getBitMap(R.drawable.megaphone));
            put("mermaid", getBitMap(R.drawable.mermaid));
            put("microphone", getBitMap(R.drawable.microphone));

            put("microscope", getBitMap(R.drawable.microscope));
            put("monkey", getBitMap(R.drawable.monkey));
            put("moon", getBitMap(R.drawable.moon));
            put("mosquito", getBitMap(R.drawable.mosquito));
            put("motorbike", getBitMap(R.drawable.motorbike));

            put("mouse-(animal)", getBitMap(R.drawable.mouse));
            put("mouth", getBitMap(R.drawable.mouth));
            put("mug", getBitMap(R.drawable.mug));
            put("mushroom", getBitMap(R.drawable.mushroom));
            put("nose", getBitMap(R.drawable.nose));

            put("octopus", getBitMap(R.drawable.octopus));
            put("owl", getBitMap(R.drawable.owl));
            put("palm-tree", getBitMap(R.drawable.palmtree));
            put("panda", getBitMap(R.drawable.panda));
            put("paper-clip", getBitMap(R.drawable.paperclip));

            put("parachute", getBitMap(R.drawable.parachute));
            put("parking-meter", getBitMap(R.drawable.parkingmeter));
            put("parrot", getBitMap(R.drawable.parrot));
            put("pear", getBitMap(R.drawable.pear));
            put("pen", getBitMap(R.drawable.pen));

            put("penguin", getBitMap(R.drawable.penguin));
            put("person-sitting", getBitMap(R.drawable.personsitting));
            put("person-walking", getBitMap(R.drawable.personwalking));
            put("piano", getBitMap(R.drawable.piano));
            put("pickup-truck", getBitMap(R.drawable.pickuptruck));

            put("pig", getBitMap(R.drawable.pig));
            put("pigeon", getBitMap(R.drawable.pigeon));
            put("pineapple", getBitMap(R.drawable.pineapple));
            put("pipe-(for-smoking)", getBitMap(R.drawable.pipe));
            put("pizza", getBitMap(R.drawable.pizza));

            put("potted-plant", getBitMap(R.drawable.pottedplant));
            put("power-outlet", getBitMap(R.drawable.poweroutlet));
            put("present", getBitMap(R.drawable.present));
            put("pretzel", getBitMap(R.drawable.pretzel));
            put("pumpkin", getBitMap(R.drawable.pumpkin));


            put("purse", getBitMap(R.drawable.purse));
            put("rabbit", getBitMap(R.drawable.rabbit));
            put("race-car", getBitMap(R.drawable.racecar));
            put("radio", getBitMap(R.drawable.radio));
            put("rainbow", getBitMap(R.drawable.rainbow));


            put("revolver", getBitMap(R.drawable.revolver));
            put("rifle", getBitMap(R.drawable.rifle));
            put("rollerblades", getBitMap(R.drawable.rollerblades));
            put("rooster", getBitMap(R.drawable.rooster));
            put("sailboat", getBitMap(R.drawable.sailboat));

            put("santa-claus", getBitMap(R.drawable.santaclaus));
            put("satellite", getBitMap(R.drawable.satellite));
            put("satellite-dish", getBitMap(R.drawable.satellitedish));
            put("saxophone", getBitMap(R.drawable.saxophone));
            put("scissors", getBitMap(R.drawable.scissor));
            put("scorpion", getBitMap(R.drawable.scorpion));

            put("screwdriver", getBitMap(R.drawable.screwdriver));
            put("sea-turtle", getBitMap(R.drawable.seaturtle));
            put("seagull", getBitMap(R.drawable.seagull));
            put("shark", getBitMap(R.drawable.shark));
            put("sheep", getBitMap(R.drawable.sheep));

            put("ship", getBitMap(R.drawable.ship));
            put("shoe", getBitMap(R.drawable.shoe));
            put("shovel", getBitMap(R.drawable.shovel));
            put("skateboard", getBitMap(R.drawable.skateboard));
            put("skull", getBitMap(R.drawable.skull));

            put("skyscraper", getBitMap(R.drawable.skyscarper));
            put("snail", getBitMap(R.drawable.snail));
            put("snake", getBitMap(R.drawable.snake));
            put("snowboard", getBitMap(R.drawable.snowboard));
            put("snowman", getBitMap(R.drawable.snowman));

            put("socks", getBitMap(R.drawable.socks));
            put("space-shuttle", getBitMap(R.drawable.spaceshuttle));
            put("speed-boat", getBitMap(R.drawable.speedboat));
            put("spider", getBitMap(R.drawable.spider));
            put("sponge-bob", getBitMap(R.drawable.spongebob));

            put("spoon", getBitMap(R.drawable.spoon));
            put("squirrel", getBitMap(R.drawable.squirrel));
            put("standing-bird", getBitMap(R.drawable.standingbird));
            put("stapler", getBitMap(R.drawable.stapler));
            put("strawberry", getBitMap(R.drawable.strawberry));

            put("streetlight", getBitMap(R.drawable.streetlight));
            put("submarine", getBitMap(R.drawable.submarine));
            put("suitcase", getBitMap(R.drawable.suitcase));
            put("sun", getBitMap(R.drawable.sun));
            put("suv", getBitMap(R.drawable.suv));

            put("swan", getBitMap(R.drawable.swan));
            put("sword", getBitMap(R.drawable.sword));
            put("syringe", getBitMap(R.drawable.syringe));
            put("table", getBitMap(R.drawable.table));
            put("tablelamp", getBitMap(R.drawable.tablelamp));

            put("teacup", getBitMap(R.drawable.teacup));
            put("teapot", getBitMap(R.drawable.teapot));
            put("teddy-bear", getBitMap(R.drawable.teddybear));
            put("telephone", getBitMap(R.drawable.telephone));
            put("tennis-racket", getBitMap(R.drawable.tennisracket));

            put("tent", getBitMap(R.drawable.tent));
            put("tiger", getBitMap(R.drawable.tiger));
            put("tire", getBitMap(R.drawable.tire));
            put("toilet", getBitMap(R.drawable.toilet));
            put("tomato", getBitMap(R.drawable.tomato));
            put("tooth", getBitMap(R.drawable.tooth));

            put("toothbrush", getBitMap(R.drawable.toothbrush));
            put("tractor", getBitMap(R.drawable.tractor));
            put("traffic-light", getBitMap(R.drawable.trafficlight));
            put("train", getBitMap(R.drawable.train));
            put("tree", getBitMap(R.drawable.tree));

            put("trombone", getBitMap(R.drawable.trombone));
            put("trousers", getBitMap(R.drawable.trousers));
            put("truck", getBitMap(R.drawable.truck));
            put("trumpet", getBitMap(R.drawable.trumpet));
            put("t-shirt", getBitMap(R.drawable.tshirt));

            put("tv", getBitMap(R.drawable.tv));
            put("umbrella", getBitMap(R.drawable.umbrella));
            put("van", getBitMap(R.drawable.van));
            put("vase", getBitMap(R.drawable.vase));
            put("violin", getBitMap(R.drawable.violin));

            put("walkie-talkie", getBitMap(R.drawable.walkietalkie));
            put("wheel", getBitMap(R.drawable.wheel));
            put("wheelbarrow", getBitMap(R.drawable.wheelbarrow));
            put("windmill", getBitMap(R.drawable.windmill));
            put("wine-bottle", getBitMap(R.drawable.winebottle));

            put("wineglass", getBitMap(R.drawable.wineglass));
            put("wrist-watch", getBitMap(R.drawable.wristwatch));
            put("zebra", getBitMap(R.drawable.zebra));
        }};
    }

    public Bitmap getImageMap(String s) {
        return imageMap.get(s);
    }

    public Bitmap getBitMap(int resId) {
        Resources res = this.c.getResources();
        int reqWidth = 200;
        int reqHeight = 200;
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }

        return inSampleSize;
    }
}