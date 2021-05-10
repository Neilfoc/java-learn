package util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 11105157
 * @Description
 * @Date 2021/1/9
 */
public class GenerateBatchSql {
    public static void main(String[] args) throws Exception {
        int count = 100;
        String originPath = "D:\\";
        String shardPath = "D:\\";

        String originFileName = "comment.sql";
        String shardFileName = originFileName.replace(".sql", "_batch.sql");

        generate(count, originPath + originFileName, shardPath + shardFileName);
        System.out.println("完成");
    }

    private static void generate(int count, String originFileName, String targetFileName) throws Exception {
        List<String> statementList = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(new File(originFileName)));

        String line;
        do {
            line = br.readLine();
            if (StringUtils.isNotBlank(line)) {
                statementList.add(line);
            }
        } while (StringUtils.isNotBlank(line));

        br.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(new File(targetFileName)));

        for (int i = 0; i < count; i++) {
            for (String statement : statementList) {
                bw.write(parseStatement(statement, i) + "\n");
            }
            bw.write("\n");
        }

        bw.flush();
        bw.close();
    }

    private static String parseStatement(String statement, int no) {
        if (statement.contains("TABLE")) {
            int fi = statement.indexOf("`");
            int i = statement.indexOf("`", fi + 1);
            if (i > 0) {
                String tableNo = StringUtils.leftPad(String.valueOf(no), 2, "0");
                statement = statement.substring(0, i) + "_" + tableNo + statement.substring(i);
            }
        }

        return statement;
    }
}
