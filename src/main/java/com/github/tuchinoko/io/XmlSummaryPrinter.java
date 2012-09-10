package com.github.tuchinoko.io;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.tuchinoko.Summary;

/**
 * <p>
 * SummaryをXML形式で出力するためのSummaryPrinterです．
 * </p><p>
 * 出力形式は，XML形式で，Processor Idがルートタグになります．
 * Summaryの各エントリがタグで表され，値がタグに囲まれます．
 * もし，Summaryのエントリキーにドット(.)が含まれている場合，
 * ドットがタグの階層となります．
 * </p><p>
 * すなわち，Processor IdがProcessorAである処理器のSummaryに以下のエントリがある場合，
 * 次のようなXMLが出力されます．
 * </p>
 * <blockquoxte><pre> summary1.key1=value11
 * summary1.key2=value12
 * summary2.key1=value21
 * summary1.key3=value13</pre></blockquoxte>
 * <blockquoxte><pre> &lt;ProcessorA timestamp="current timestamp"&gt;
 *   &lt;summary1&gt;
 *     &lt;key1&gt;value11&lt;/key1&gt;
 *     &lt;key2&gt;value12&lt;/key2&gt;
 *     &lt;key3&gt;value13&lt;/key3&gt;
 *   &lt;/summary1&gt;
 *   &lt;summary2&gt;
 *     &lt;key1&gt;value21&lt;/key1&gt;
 *   &lt;/summary2&gt;
 * &lt;/ProcessorA&gt;</pre></blockquote>
 *
 * @author Haruaki Tamada
 */
public class XmlSummaryPrinter extends SummaryPrinter{
    /**
     * <p>
     * 指定されたSummaryを指定された出力先へ出力します．
     * 出力形式はXML形式です．
     * </p>
     */
    @Override
    public void print(Summary summary, PrintWriter out){
        String name = summary.getProcessorId();
        out.printf(
            "  <%s timestamp=\"%s\">%n",
            name, DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime())
        );
        Tree[] treeRoot = buildTree(summary);
        for(Tree tree: treeRoot){
            printTree(out, tree, "    ");
        }
        out.printf("  </%s>%n", name);
    }

    private void printTree(PrintWriter out, Tree parent, String indent){
        if(parent.getSize() > 0){
            String childIndent = indent + "  ";
            out.printf("%s<%s>%n", indent, parent.tag);
            for(Tree tree: parent.nodes.values()){
                printTree(out, tree, childIndent);
            }
            out.printf("%s</%s>%n", indent, parent.tag);
        }
        else{
            out.printf("%s<%s>%s</%s>%n", indent, parent.tag, parent.value, parent.tag);
        }
    }

    private Tree[] buildTree(Summary summary){
        Tree root = new Tree("root");

        for(Summary.Entry entry: summary){
            String name = entry.getKey();
            String[] tags = name.split("\\.");
            Tree parent = root;
            for(int i = 0; i < tags.length; i++){
                Tree tree = parent.getNode(tags[i]);
                if(tree == null){
                    tree = new Tree(tags[i]);
                    parent.putNode(tags[i], tree);
                }
                parent = tree;
            }
            parent.setValue(entry.getValue());
        }
        return root.getNodes();
    }

    private static class Tree{
        private Map<String, Tree> nodes = new LinkedHashMap<String, Tree>();
        private String tag;
        private String value;

        public Tree(String tag){
            this.tag = tag;
        }

        public int getSize(){
            return nodes.size();
        }

        public Tree[] getNodes(){
            return nodes.values().toArray(new Tree[nodes.size()]);
        }

        public String getTag(){
            return tag;
        }

        public String getValue(){
            return value;
        }

        public void setValue(String value){
            if(value == null){
                throw new NullPointerException();
            }
            this.value = value;
        }

        public Tree getNode(String key){
            return nodes.get(key);
        }

        public void putNode(String key, Tree tree){
            nodes.put(key, tree);
        }
    }
}
