import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class VietTokenizer {
    @SuppressWarnings("serial")
    private static final Map<String, String> POS = new HashMap<String, String>(){{
        put("N", "danh từ");
        put("Np", "danh từ riêng");
        put("Nc", "danh từ chỉ loại");
        put("Nu", "danh từ đơn vị");
        put("V", "động từ");
        put("A", "tính từ");
        put("P", "đại từ");
        put("L", "định từ");
        put("M", "số từ");
        put("R", "phụ từ");
        put("E", "giới từ");
        put("C", "liên từ");
        put("I", "thán từ");
        put("T", "trợ từ, tiểu từ");
        put("B", "loan word");
        put("Y", "từ viết tắt");
        put("X", "un-các từ không phân loại được");
        put("Mrk", "các dấu câu");

    }};
    private final JVnTextPro textProcessor;

    public VietTokenizer(Properties props) {
        props.putIfAbsent("sentSegModel",
                          "C:/Users/Nate/Desktop/workspaces/java_workspace/JVnTextPro-v.2.0/models/jvnsensegmenter");
        props.putIfAbsent("sentSegModel",
                          "C:/Users/Nate/Desktop/workspaces/java_workspace/JVnTextPro-v.2.0/models/jvnsensegmenter");
        props.putIfAbsent("wordSegModel",
                          "C:/Users/Nate/Desktop/workspaces/java_workspace/JVnTextPro-v.2.0/models/jvnsegmenter");
        props.putIfAbsent("posTaggerModel",
                          "C:/Users/Nate/Desktop/workspaces/java_workspace/JVnTextPro-v.2.0/models/jvnpostag/maxent");
        textProcessor = new JVnTextPro();
        textProcessor.initSenSegmenter(props.getProperty("sentSegModel"));
        textProcessor.initSenTokenization();
        textProcessor.initSegmenter(props.getProperty("wordSegModel"));
        textProcessor.initPosTagger(props.getProperty("posTaggerModel"));
    }

    public Result tokenize(String inputText) {
        // important for getting token indices right
        inputText.replaceAll("([\\v\\s\\t]+)", "$1");
        String taggedText = textProcessor.process(inputText);
        List<Token> tokens = new LinkedList<>();
        System.out.println(taggedText);
        // we search for the tokenized item in the input string so we can mark
        // the start/end locations; this works because JVnTextPro doesn't change
        // the tokens except to join syllables in a word with underscores.
        int currentIndex = 0;
        for (String s : taggedText.split(" ")) {
            String[] tagged = s.split("/");
            // ignore tokenized punctuation
            if (Character.isAlphabetic(tagged[1].charAt(0))) {
                String lemma = tagged[0].replace('_', ' ');
                currentIndex = inputText.indexOf(lemma, currentIndex - 1);
                Map<String, String> details = new HashMap<>();
                String pos = POS.get(tagged[1]);
                if(POS != null)
                    details.put("POS", pos);
                tokens.add(new Token(currentIndex, currentIndex + lemma.length() - 1, lemma, details));
            }
        }
        return new Result(inputText, tokens);
    }

    public static void main(String[] args) {
        VietTokenizer vt = new VietTokenizer(new Properties());
        Result result = vt.tokenize("Xử lý ngôn ngữ là một kĩ thuật quan trọng nhằm giúp máy "
                                    + "tính hiểu được ngôn ngữ của con người, qua đó hướng dẫn máy "
                                    + "tính thực hiện và giúp đỡ con người trong những công việc có "
                                    + "liên quan đến ngôn ngữ như : dịch thuật, phân tích dữ liệu "
                                    + "văn bản, nhận dạng tiếng nói, tìm kiếm thông tin, ... XLNN "
                                    + "cũng đóng một vai trò quan trọng trong việc đẩy mạnh sự phát "
                                    + "triển của CNTT Việt Nam để sánh ngang với các cường quốc khác.");
        System.out.println(result);
    }

}
