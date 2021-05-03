package com.shata.helpmyapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shata.helpmyapp.Model.ModelDisease;
import com.shata.helpmyapp.R;
import com.shata.helpmyapp.databinding.ActivityAddDiseasesBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class AddDiseasesActivity extends AppCompatActivity {

    ActivityAddDiseasesBinding diseasesBinding;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private long backPressedTime;
    private Toast backToast;

    static ModelDisease modelDisease = null;
    String key = "";
    boolean isDelete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        diseasesBinding = ActivityAddDiseasesBinding.inflate(getLayoutInflater());
        setContentView(diseasesBinding.getRoot());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Diseases");

        setSupportActionBar(diseasesBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Add Data
        /*
        List<String> ti = new ArrayList<>();
        List<String> di = new ArrayList<>();
        List<String> tr = new ArrayList<>();

        ti.add("تسوس الاسنان");
        ti.add("الاكتئاب");
        ti.add("الحساسية");
        ti.add("مرض السكري Diabetes");
        ti.add("ارتفاع الكولسترول\n" +
                "high Blood CholesteroL");
        ti.add("حب الشباب\n" +
                "Acne");
        ti.add("ضغط الدم ارتفاع");
        ti.add("أمراض القلب");
        ti.add("الأنفلونزا\n" +
                "Influenza");
        ti.add("فقر الدم\n" +
                "Anemia");
        ti.add("تساقط الشعر\n" +
                "Hair Loss");
        ti.add("عسر الهضم\n" +
                "Dyspepsia");
        ti.add("قصر النظر\n" +
                "Myopia, Nearsightedness");
        //////////////////////////////////////////////////////

        di.add("تسوس الاسنان هو عبارة عن أجزاء من أسنان مصابة بالتعفن الذي قد يتطور إلى ثقوب صغيرة أو كبيرة بشكل تدريجي\n" +
                "علاج تسوس الاسنان يتعلق، بشكل كبير، بدرجة التسوس ومدى خطورته وبالحالة الصحية بشكل عام\n");
        di.add("لاكتئاب (Depression) ليس ضعفا أو شيئا سهل التخلص منه، ويعرف بأنه الاضطراب الاكتئابي الحاد الاكتئاب السّريري (الإكلينيكي - Clinical depression).\n" +
                "\n" +
                "هو مرض يصيب النفس والجسم. يؤثر الاكتئاب على طريقة التفكير والتصرف ومن شأنه أن يؤدي إلى العديد من المشاكل العاطفية والجسمانية.\n");
        di.add("الحساسية (الأَرَجيّة – Allergie / Allergy) هي ردة فعل جهاز المناعة (Immune system) لمواد غير مألوفة له، مثل حبيبات اللقاح، السم من جراء لسعة النحل أو وبر الحيوانات.\n" +
                "\n" +
                "ينتج جهاز المناعة بروتينا يدعى الضدّ (أو: الجسم المضاد - Antibody .IgE). هذه الأضداد تحمي الجسم من الأجسام الغريبة غير المرغوب فيها، \n" +
                "على الرغم من إنه ليس كذلك، مما يؤدي إلى إفراز الهيستامين (Histamine) ومواد أخرى تسبب ظهور أعراض الحساسية.\n");
        di.add("يشمل مصطلح مرض السكري (Diabetes) عددا من الاضطرابات في عملية هدم وبناء - الأيض - (Metabolism) الكربوهيدرات.\n" +
                "\n" +
                "عملية الأيض الطبيعية\n" +
                "الكربوهيدرات التي يحصل عليها الجسم من تناول الخبز، البطاطا، الأرز، الكعك وغيرها من أغذية عديدة أخرى، تتفكك وتتحلل بشكل تدريجي.\n" +
                "\n" +
                "تبدأ عملية التفكك والتحلل هذه في المعدة، ثم تستمر في الإثني عشر (Duodenum) وفي الأمعاء الدقيقة.\n" +
                "\n" +
                "تنتج عن عملية التفكك والتحلل هذه مجموعة من السكريات (كربوهيدرات - Carbohydrate) يتم امتصاصها في الدورة الدموية.\n");
        di.add("الكولسترول (cholesterol) هو مركب موجود في كل خلية من خلايا الجسم، ويقوم باستعماله لبناء خلايا جديدة ومعافاة، ولإنتاج هورمونات ضرورية له.");
        di.add("حب الشباب، العُدّ او البثور، بغض النظر عن التسمية، قد تشكل مصدر إزعاج يومي دائم. الشفاء من حب الشباب يتم ببطء. فما أن تبدأ بالاختفاء حتى تظهر أخرى غيرها كانت في الانتظار.");
        di.add("بعض الناس يعانون من ارتفاع ضغط الدم (High blood pressure) لسنواتٍ دونَ أن يشعروا بأي عَرَض.\n" +
                " ضغط الدم المرتفع وغير المُراقَب يزيد من احتمالات الإصابة بمشاكل صحية خطيرة، كالنوبة القلبية والسكتة الدماغية\n");
        di.add("قد تؤثر امراض القلب (Heart disease) على أي من وظائف القلب وعلى أي من أجزاء القلب، ويعد مرض القلب الأكثر شيوعا هو المتلازمة الإكليلية (أو: متلازمة الشريان التاجي - Coronary syndrome)  \n" +
                "بأشكالها وتجلياتها المختلفة.\n" +
                "\n" +
                "الأوعية الدموية التاجية هي الأوعية الدموية المنتشرة على الجانب الخارجي من عضلة القلب ووظيفتها توصيل الدم إلى القلب نفسه.\n");
        di.add("النزلة الوافدة (الأنفلونزا - Influenza) هو مرض فيروسي يتميز بالتفشي الموسميّ، على نطاق واسع. المسبب له هو فيروس الأنفلونزا الذي ينتقل من شخص إلى آخر عن طريق الجهاز التنفسي.");
        di.add("فقر الدم (الأنيميا - Anemia) هو حالة طبية تتميز بعدم وجود كمية كافية من خلايا الدم الحمراء في الجسم لتنقل كمية كافية من الأكسجين إلى الأنسجة.\n" +
                "\n" +
                "الإنسان الذي يعاني من فقر الدم من المرجح أن يشعر بالتعب في أحيان متقاربة.\n");
        di.add("أي شخص يُلاحظ أن شعره قد بدأ يتساقط، يخاف، أو من يلاحظ وجود كمية كبيرة (شاذة) من الشعر على المشط أو فرشاة الشعر، من المفضل أن يتوجه على الفور إلى طبيب اختصاصي بالأمراض الجلدية ");
        di.add("عسر الهضم (dyspepsia) هو مصطلح طبي يصف اضطرابا في مركزه الشعور السلبي المتركز في الجزء العلوي من المعدة. هذا الشعور السيء قد يظهر على شكل  ");
        di.add("قصر النظر او الحـَسَر (Nearsightedness / Myopia) هو مسبب شائع للرؤية المشوشة. يرى الأشخاص المصابون بقصر النظر الأشياء البعيدة بشكل مشوش وغير واضح. أحيانا، يمكن الحـَوَل (Strabismus)، \n" +
                "أو تقليص العينين بهدف رؤية الأشياء بوضوح.\n" +
                "\n" +
                "بشكل عام، يشكل الحسر ضعفا في الرؤية (البصر)، وليس مرضا. في حالات قليلة يكون قصر النظر نتيجة لمرض آخر. \n");

        ///////////////////////////////////////////////////////

        tr.add("العلاج بالفلوريد\n" +
                "الحشوات المركّبة (أو: الحشوات التعويضية - Composite fillings)\n" +
                "علاج جذر السن (أو: علاج العصب)\n" +
                "التاج (غطاء كامل للسن يستخدم لترميم وإصلاح الأسنان التالفة)\n" +
                "خلع (قلع الأسنان)\n");
        tr.add("المعالجة الدوائية\n" +
                "المعالجة النفسانية\n" +
                "المعالجة بالتخليج الكهربيّ (المعالجة بالصدمة الكهربائية - Electroconvulsive treatment Electroshock treatment - ECT)\n" +
                "كما إن هنالك طرق لعلاج الاكتئاب لم تستوف البحث والتجريب مثل الطرق المقبولة المذكورة أعلاه، من بينها:\n" +
                "\n" +
                "التنبيه (التحفيز) الدماغي\n");
        tr.add("علاج الحساسية يشمل:\n" +
                "\n" +
                "تجنّب المستأرجات\n" +
                "استعمال أدوية لتخفيف الأعراض\n" +
                "تناول علاج مناعي\n" +
                "استعمال الأدرينالين (إبِينيفرِين) في حالات الطوارئ.\n");
        tr.add("أدوية الـ GLP-1: تعمل هذه الأدوية بواسطة دور البيبتيدات في الجهاز الهضمي على توازن تركيز الجلوكوز في الدم ومنها ال GLP-1. من التأثيرات الجانبية المعروفة لهذا الدّواء تخفيض الوزن، التقيّؤ، الغثيان والإسهال.\n" +
                "الحقن\n" +
                "الانسولين: أصبح العلاج بواسطة الانسولين شائعًا أكثر في الفترة الأخيرة، رغم رفض العديد من المرضى تقبّل العلاج بواسطة حقن بشكل يومي. ينقسم علاج الأنسولين إلى نوعين:\n" +
                "العلاج بواسطة انسولين ذو فعالية طويلة الأمد، وهو عبارة عن حقن يومية توفر للجسم كمية الانسولين الأساسية\n");
        tr.add("إستاتين (Statins): هو الدواء الأكثر شيوعا اليوم لعلاج الكولسترول و لخفض مستوى الكولسترول في الدم، إذ يعيق إفراز المادة اللازمة لإنتاج الكولسترول في الكبد.\n" +
                "أدوية تربط الأحماض الصفراوية (Bile - acid - binding resins):\n" +
                "الفيبرات (fibrates): الأدوية لوفيبرا (Lofibra)، \n" +
                "تريكورفينوفيبرات (TriCorfenofibrate)، \n" +
                "لوبيد (Lopid)،\n" +
                " جيفيبرزول (gemfibrozil)\n");
        tr.add("كل أنواع علاج حب الشباب (العد) المقترحة لا تعطي نتائج فوريّة، بل تبدأ النتائج بالظهور بعد 4 - 8 أسابيع، حتى إن الوضع قد يسوء في حالات معيّنة قبل ظهور التحسّن.\n" +
                "\n" +
                "1- علاجات موضعية\n" +
                "دَهونات ومستحلبات علاج حب الشباب أو العُدّ تعمل على تجفيف العصارة الحليبيّة والزيوت، تقضي على الجراثيم، \n" +
                "وتحفّز تساقط خلايا الجلد الميّتة. المستحضرات التي لا تستلزم وصفات طبيّة تكون ناعمة بشكل عام وتحتوي على مواد فعّاله\n" +
                " مثل: بنزويل بيروكسيد (benzoyl peroxide)، كبريت، رسورتزينول، حمض الساليسيليك (salicylic acid) وحمض اللكتيك. هذه المواد تكون فعّاله عندما تكون البثور خفيفة.\n" +
                "\n" +
                "2- الادوية\n" +
                "أما إذا لم تتجاوب البثور مع هذه العلاجات، فمن المفضّل التوجه إلى طبيب العائلة أو طبيب الجلد للحصول على وصفة طبيّة لدَهونات أو مستحلبات أكثر فاعلية.\n" +
                " حمض الرتينول وأدبلن هما نموذجان من الأدوية للعلاج الموضعي، المُنتَجَة على أساس فيتامين (أ).\n" +
                "\n" +
                "هذه الأدوية تعتمد على تحفيز تجدد الخلايا، وتمنع تكوّن الانسدادات في بصيلة الشعرة. \n" +
                "بالاضافة الى ذلك، هنالك أدوية معيّنة تحاكي بفاعليّتها المضادّات الحيويّة المستعملة للعلاج الموضعي. \n" +
                "هذه الأدوية تعمل على القضاء على الجراثيم الموجودة على سطح الجلد. للحصول على النتيجة الفُضْلى قد يحتاج المُعالِج لأن يدمج بين أكثر من دواء واحد في الوقت نفسه، مثل:\n" +
                "\n" +
                "مضادات حيويّة\n" +
                " ايزوتراتينوين (Isotretinoin)\n" +
                "وسائل وقائيّة تؤخذ فمويًّا\n" +
                "علاج حب الشباب بالليزر\n" +
                "علاجات تجميلية: تقشير البشرة بواسطة مواد كيماويّة أو البَرْد / الكَشط الدقيق للأدمة (microdermabrasion) - قد تكون ناجعة لعلاج حب الشباب.\n" +
                "باستطاعة الطبيب إتباع إجراءات معيّنة لمعالجة وتخفيف النّـُدَب التي يخلّفها حب الشباب، بما فيها:\n" +
                "\n" +
                "ملء الأنسجة الرخوة\n" +
                "تقشير الجلد المشوّه\n" +
                "كشط الأدمة (Dermis - إحدى طبقات الجلد وتقع مباشرة تحت البشرة)\n" +
                "علاج بالليزر، مصدر ضوئي وعلاج بواسطة موجات راديَويّة (Radio waves)\n" +
                "عمليّة جراحيّة للجلد.\n");
        tr.add("ادوية لعلاج ضغط الدم\n" +
                "يتعلق ارتفاع الضغط وعلاجه او علاج ضغط الدم المرتفع الذي يوصي به الطبيب بمستوى ضغط الدم عند المريض وبالمشاكل الطبية الأخرى التي يعاني منها. من بين الادوية الموصى بها:\n" +
                "\n" +
                "مُدرّات البول (Diuretic) من مجموعة التيازيد (Thiazide)\n" +
                "مُحْصِرات المُسْتَقْبِلاتِ البيتا (Beta - blocker)\n" +
                "مثبطات الإنزيم المحول للأنجيوتنسين (ACE)\n" +
                "مُحْصِرات مستقبل الأنجيوتنسين 2\n" +
                "محصرات قنوات الكالسيوم\n" +
                "مثبطات الرينين (Renin)\n" +
                "في الحالات التي لا يمكن فيها السيطرة على ارتفاع الضغط وعلاجه بمساعدة الأدوية المذكورة أعلاه، من الممكن أن يوصي الطبيب بتناول الأدوية التالية:\n" +
                "\n" +
                "مُحْصِرات مستقبلات الألفا (Alpha blocker)\n" +
                "محصرات مستقبلات الألفا - بيتا (Alpha - Beta blocker)\n" +
                "مُوَسّعات الأوعية الدموية.\n" +
                "بزر القطـوناء (Blond psyllium)\n" +
                "الكالسيوم\n" +
                "الكاكاو\n" +
                "زيت كبد سمك القدّ ( Cod liver oil)\n" +
                "تميم الإنزيم Q-10 Coenzyme) Q-10)\n" +
                "الأحماض الدهنية أوميغا 3\n" +
                "الثوم.\n");
        tr.add("علاج امراض القلب الوعائية\n" +
                "الهدف من وراء علاج امراض القلب الوعائية (Cardiovascular diseases) هو، غالبا، فتح الشرايين المُضْيَقّة أو المغلقة التي تسبب أعراضا.\n" +
                "\n" +
                "نوع العلاج يتوقف على شدة التضيّق ويمكن أن يشمل تغييرات في أسلوب الحياة وفي العادات، بعض الأدوية، بعض الإجراءات الطبية أو عملية جراحية.\n" +
                "\n" +
                "علاج اضطرابات نظم القلب\n" +
                "معالجة اضطرابات نَظم القلب يمكن أن تشمل الأدوية، الإجراءات الطبية، ناظمة اصطناعية (Artificial pacemaker) لتنظيم ضربات القلب، \n" +
                "زرع جهاز مزيل الرَّجَفان (Defibrillator)، عملية جراحية وتحفيز العصب المبهم (تحفيز مُبهَمِيّ – StimulationVagal).\n" +
                "المعالجة بالأدوية\n" +
                "الأجهزة الطبية\n" +
                "عملية زراعة قلب.\n" +
                "علاج التلوثات\n" +
                "علاجات مرض القلب المرتبطة بالصمامات تختلف طبقا لنوع الصمام المصاب ودرجة الخطورة، ولكنها تشمل، بصفة عامة، الأدوية، الفتح بواسطة البالون، الإصلاح والترميم أو استبدال الصمام.\n");
        tr.add("يتم علاج الأنفلونزا بأدوية لتخفيف الأعراض، مثل: الأدوية لخفض درجة حرارة الجسم ولتخفيف مُخاطية الأنف والسّعال.\n" +
                "\n" +
                "تاميفلو (Tamiflu)\n" +
                "ريلينزا (Relenza)\n" +
                "فعّالان فقط إذا تم تناولهما في اليومين الأولين من الإصابة بمرض الأنفلونزا، أو كوقاية بعد التعرض للفيروس.\n");
        tr.add("تتنوع علاجات فقر الدم وتختلف تبعاً للمسبب، وهي على النحو التالي:\n" +
                "\n" +
                "علاج فقر الدم الناجم عن عوز الحديد: يتم علاج فقر الدم من هذا النوع، في أغلب الحالات، بواسطة تناول مكملات (مضافات) الحديد.\n" +
                "علاج فقر الدم الناجم عن عوز الفيتامينات: هو نوع صعب من فقر الدم تتم معالجته بواسطة حُقن تحتوي على فيتامين B - 12، وقد يستمر ذلك، في بعض الحالات، مدى الحياة.\n" +
                "علاج فقر الدم المصاحب للأمراض المزمنة: ليس هنالك علاج معين لهذا النوع من فقر الدم.\n" +
                "علاج فقر الدم اللاتنسجي: قد يشمل علاج فقر الدم من هذا النوع باعطاء الدم وريديا (Intravenous feeding) لرفع كمية خلايا الدم الحمراء في الجسم.\n" +
                ".\n" +
                "\n" +
                "الحديد\n" +
                "حمض الفوليك\n" +
                "فيتامين B 12\n" +
                "فيتامين C\n");
        tr.add("1- عملية جراحية لترميم الشعر\n" +
                "يجري الأطباء والجراحون المختصون بالأمراض الجلدية عدة أنواع من العمليات الجراحية التي تهدف إلى استعادة الشعر، ترميمه وإعادة ترميم الأماكن التي تساقط منها، إضافة إلى منح الشعر منظرا طبيعيا، قدر الإمكان.\n" +
                "\n" +
                "أما أكثر الأشخاص المرشحين للخضوع لمثل هذه العمليات الجراحية، وأكثرهم حاجة إليها، فهم الذين يبدو الصلع لديهم بارزا للعيان، الذين لديهم شعر خفيف جدا \n" +
                "والذين يتساقط شعرهم من جراء إصابة فروة الرأس أو من جراء إصابتهم بحروق.\n" +
                "\n" +
                "يتحدد نوع العملية التي ينبغي إجراؤها لترميم الشعر طبقا لمدى انتشار الصلع وشكله. وبإمكان طبيب الأمراض الجلدية أن ينصح بأحد الأنواع المفصلة أدناه من العمليات الجراحية، من أجل الحصول على أفضل نتيجة ممكنة.\n" +
                "\n" +
                "2- زرع الشعر (Hair Transplantation)\n" +
                "يعتمد زرع الشعر على مبدأ \"التبرع السائد\" (المفضل)، أي أخذ الشعر من مكان سليم وزرعه، خلال العملية الجراحية، لكي ينمو مجددا في المكان المصاب بالصلع. عملية زرع الشعر تتطلب الأمور التالية:\n" +
                "\n" +
                "نزع (إزالة) أتلام (Streaks) من جلد فروة الرأس: والتي تحتوي الشعر من المنطقة الخلفية والجوانب في فروة الرأس (هذه الأماكن تسمى \"مناطق التبرع\"، نظرا لأنها تحتوي على شعر يستمر بالنمو مدى الحياة).\n" +
                "إصلاح وترميم \"منطقة التبرع\": وهي عملية تكون نتيجتها، عادة، ظهور ندبة صغيرة، يغطيها الشعر المحيط بها.\n" +
                "قص أتلام من جلد فروة الرأس: التي تحتوي على الشعر، من منطقة التبرع، وتقسيمها إلى مجموعات غِرسات (طُعوم - Implants) من أجل زرعها في بقع الصلع المعدّة لذلك.\n" +
                "تختلف المساحة التي يمكن تغطيتها بواسطة زرع الشعر باختلاف حجم بقعة الصلع وطريقة الزرع المتبعة.\n" +
                "\n" +
                "بعد نحو شهر من العملية، يتساقط الجزء الأكبر من الشعر المزروع. وبعد شهرين من ذلك، يبدأ شعر جديد بالنمو، ثم يواصل النمو وفق الوتيرة الطبيعية. \n" +
                "وبعد ستة أشهر يكتسب الشعر المزروع شكلا ومظهرا مماثلين لشكل الشعر الطبيعي ومظهره.    \n" +
                "\n" +
                "3- تقليص (جلد) فروة الرأس\n" +
                "يعتبر تقليص جلدة الرأس حلاً لمن يعانون من اتساع رقعة الصلع.\n" +
                "\n" +
                "هذا العلاج عبارة عن عملية جراحية يتم خلالها تضييق حيز البقع الصلعاء، بل ويتم ، في بعض الحالات، إخفاؤها تماما، من خلال إزالة بعض السنتيمترات من الجلد الخالي من الشعر، \n" +
                "وبعد ذلك شد طرفي القطع باتجاه بعضهما البعض وتوصيلهما بالخياطة.\n" +
                "\n" +
                "بالإمكان إجراء عملية تقليص فروة الرأس لوحدها، أو دمجها مع عملية أخرى لزرع الشعر.    \n" +
                "\n" +
                "علاج تساقط الشعر\n" +
                "\n" +
                "4- توسيع جلد فروة الرأس/ شد الأنسجة\n" +
                "يتم زرع جهازين تحت جلد فروة الرأس لمدة ثلاثة أو أربعة أسابيع.\n" +
                "\n" +
                "تكون وظيفة هذين الجهازين شد الجلد الذي يحمل الشعر، وذلك بغية تنجيع وتحسين نتائج العملية الجراحية لتقليص جلد فروة الرأس.\n" +
                "\n" +
                "يعمل جهاز الشدّ بتقنية تشبه عمل الشريط المرن، بينما يعمل جهاز التوسيع مثل البالون. وهما يتيحان تقليص مساحة الجلد الخالي من الشعر (الأصلع) على فروة الرأس.\n");
        tr.add("في الحالات الخفيفة من المرض لا نحتاج إلى علاج عسر الهضم. تبديد التوتر والمخاوف يشكل علاجا كافيا. أما في الحالات الأصعب، \n" +
                "فإن الأدوية التي تعادل، أو تقلل، من إفرازات أحماض المعدة يمكنها أن تخفف الأعراض وتساعد الكثيرين من المرضى. القضاء على جرثومة الملوية البوابية (Helicobacter pylori)، \n" +
                "إذا ثبت وجود تلوث بهذه الجرثومة، من شأنه أن يساعد بعض المرضى. وقد تم تجريب الأدوية التي تعالج الامساك وتسرّع من وتيرة إفراغ المعدة، لكن فاعليتها لا تزال موضع شك وخلاف.\n" +
                "\n" +
                "في نظرة إلى المستقبل، يؤمن الباحثون بأن حل هذه المشكلة يكمن في الأدوية التي تؤثر على حساسية المعدة وعلى قدرة جدار المعدة على التكيف والارتخاء. \n" +
                "وهنالك عدد من الأدوية ذات هذه الخصائص التي لا تزال، اليوم، موضع بحث واختبار.\n");
        tr.add("لا يمكن إشفاء الحسر، ولكن يمكن الوصول بواسطة العلاج إلى رؤية طبيعية،أو شبه طبيعية، والتغلب على قصر النظر.\n" +
                "\n" +
                "معظم الأشخاص المصابين بقصر النظر يضعون النظارات أو العدسات اللاصقة، بهدف تصحيح الرؤية. هذا هو علاج قصر النظر المقبول والمتبع. ولكن، هنالك إمكانية لمعالجة قصر النظر بواسطة الجراحة.\n" +
                "\n" +
                " العدسات التصحيحية تركز الضوء من جديد، بحيث يقع على الشبكية. يختار معظم الأشخاص المصابين بقصر النظر استخدام النظارات أو العدسات اللاصقة لمعالجة المشكلة. \n" +
                "كلتا الوسيلتين آمنة وفعالة وكلتاهما أقل خطرا وتكلفة من الجراحة.\n" +
                "\n" +
                "الجراحة تغيّر شكل القرنية. هنالك عدة عمليات جراحية، مثل جراحة الليزر التي تسمى LASIK- استئصال القرنية تحت الظهارية بواسطة الليزر، عملية جراحية لزرع الحلقات في محيط القرنية وزرع عدسات في داخل العينين (IOL). \n" +
                "\n" +
                "انظر إلى القائمة العامة للحسنات والنواقص لـ:\n" +
                "\n" +
                "العدسات اللاصقة\n" +
                "الجراحة\n" +
                "النظارات\n");
        ////////////////////////////////////////////////////////////////////

        Thread thread
                = new Thread(() -> {
                    for (int i = 0; i < ti.size(); i++) {

                        ModelDisease disease = new ModelDisease();
                        String k = databaseReference.push().getKey();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        disease.setdID(k);
                        disease.setTitle(ti.get(i));
                        disease.setDescription(di.get(i));
                        disease.setTreatments(tr.get(i));
                        databaseReference.child(k).setValue(disease).addOnCompleteListener(task -> {
                            //Toast.makeText(AddDiseasesActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        });
                    }
                    runOnUiThread(() -> Toast.makeText(AddDiseasesActivity.this, "Done", Toast.LENGTH_SHORT).show());
                });

        thread.start();
*/

        modelDisease = (ModelDisease) getIntent().getSerializableExtra("DISEASE_SELECT");
        if (modelDisease != null) {
            isDelete = true;
            key = modelDisease.getdID();
            diseasesBinding.nameDiseases.setText(modelDisease.getTitle());
            diseasesBinding.descriptionDiseases.setText(modelDisease.getDescription());
            diseasesBinding.treatmentDiseases.setText(modelDisease.getTreatments());
        }
        diseasesBinding.saveDiseases.setOnClickListener(v -> {
            Updata_SaveDiseases();
        });
    }

    private void Updata_SaveDiseases() {

        ModelDisease disease = new ModelDisease();

        if (modelDisease != null) {
            key = modelDisease.getdID();
        } else {
            key = databaseReference.push().getKey();
        }

        diseasesBinding.progressCircle.setVisibility(View.VISIBLE);

        String NameDiseases = Objects.requireNonNull(diseasesBinding.nameDiseases.getText()).toString().trim();
        if (NameDiseases.isEmpty()) {
            diseasesBinding.nameDiseases.setError("" + getResources().getString(R.string.the_name));
            diseasesBinding.nameDiseases.setFocusable(true);
            diseasesBinding.nameDiseases.requestFocus();
            diseasesBinding.progressCircle.setVisibility(View.GONE);
            return;
        }

        String DescriptionDiseases = Objects.requireNonNull(diseasesBinding.descriptionDiseases.getText()).toString().trim();
        if (DescriptionDiseases.isEmpty()) {
            diseasesBinding.descriptionDiseases.setError("" + getResources().getString(R.string.the_description));
            diseasesBinding.descriptionDiseases.setFocusable(true);
            diseasesBinding.descriptionDiseases.requestFocus();
            diseasesBinding.progressCircle.setVisibility(View.GONE);
            return;
        }

        String TreatmentDiseases = Objects.requireNonNull(diseasesBinding.treatmentDiseases.getText()).toString().trim();
        if (TreatmentDiseases.isEmpty()) {
            diseasesBinding.treatmentDiseases.setError("" + getResources().getString(R.string.the_treatment));
            diseasesBinding.treatmentDiseases.setFocusable(true);
            diseasesBinding.treatmentDiseases.requestFocus();
            diseasesBinding.progressCircle.setVisibility(View.GONE);
            return;
        }

        disease.setdID(key);
        disease.setTitle(NameDiseases);
        disease.setDescription(DescriptionDiseases);
        disease.setTreatments(TreatmentDiseases);

        databaseReference.child(key).setValue(disease).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.isComplete()) {
                diseasesBinding.progressCircle.setVisibility(View.GONE);
                Toasty.success(AddDiseasesActivity.this, "" + getResources().getString(R.string.add_done), Toast.LENGTH_SHORT).show();
                if (modelDisease != null) {
                    startActivity(new Intent(AddDiseasesActivity.this, HomeActivity.class));
                    finish();
                } else {
                    diseasesBinding.nameDiseases.setText("");
                    diseasesBinding.descriptionDiseases.setText("");
                    diseasesBinding.treatmentDiseases.setText("");
                }

            } else {
                diseasesBinding.progressCircle.setVisibility(View.GONE);
            }
        }).addOnFailureListener(e -> {
            diseasesBinding.progressCircle.setVisibility(View.GONE);
        });

        if (modelDisease != null) {
            finish();
        } else {
            diseasesBinding.nameDiseases.setText("");
            diseasesBinding.descriptionDiseases.setText("");
            diseasesBinding.treatmentDiseases.setText("");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isDelete)
            getMenuInflater().inflate(R.menu.diseases_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete:
                delete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void delete() {
        databaseReference.child(key).removeValue().addOnCompleteListener(task -> {
            Toasty.success(AddDiseasesActivity.this, "" + getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> Toasty.success(AddDiseasesActivity.this, "" + getResources().getString(R.string.delete_failed), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return;
        } else {
            backToast = Toasty.warning(getBaseContext(), "" + getResources().getString(R.string.press_again_toback), Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

}