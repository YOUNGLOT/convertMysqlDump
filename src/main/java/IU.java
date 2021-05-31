import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IU {
    public static void main(String[] args) {
        IU iu = new IU();
//        iu.solve("MYSQL_Hydrak_Table_Only.sql");

        String folderDirectory = "MYSQL_Hydrak_Data_All/";
        String[] fileNameArray = iu.getfileNames("./data/"+folderDirectory);
        for (int i = 0; i < fileNameArray.length; i++) {

            System.out.println(String.format(" 시작합니당 : %s 파일 시작!", fileNameArray[i]));

            String fileDirectory = String.format("%s/%s", folderDirectory, fileNameArray[i]);

            iu.solve(fileDirectory);

        }

    }

    @SneakyThrows
    public void solve(String fileName){
        BufferedReader bufferedReader = getFile("./data/" + fileName);
        String line = "", result = "";

        try{

            while ((line = bufferedReader.readLine()) != null) {
                result += formatTableNameToUpperCase(line) + "\n";
            }

            FileWriter fw = new FileWriter(new File("./formatedData/" + fileName), true) ;

            fw.write(result);
            fw.flush();
            fw.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String formatTableNameToUpperCase(String line){
        String str1 = line;//clone 생성
        List<Integer> arrayList = new ArrayList<>();
        int i = 0;

        //  ` 의 index 를 List 에 ADD
        while (str1.indexOf("`") != -1) {
            arrayList.add(str1.indexOf("`"));
            str1 = str1.substring(0, arrayList.get(i)) + "-" + str1.substring(arrayList.get(i) + 1);
            i++;
        }

        //  ` 가져온 index List 를 이용하여 대문자로 치환
        for (int j = 0; j < arrayList.size() / 2; j++) {

            int idx1 = arrayList.get(2 * j);
            int idx2 = arrayList.get(2 * j + 1);

            line = line.substring(0, idx1) + line.substring(idx1, idx2).toUpperCase() + line.substring(idx2);
        }
        return line;
    }

    private BufferedReader getFile(String fileDirectory) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileDirectory)), "UTF-8"));
            return br;
        } catch (Exception e) {
            System.out.println("FileReader Exception : " + e);
            return null;
        }
    }

    public String[] getfileNames(String directory){
        File path = new File(directory);

        final String pattern = "";

        String[] fileList = path.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(pattern);
            }
        });
        if (fileList.length > 0) {
            for (int i = 0; i < fileList.length; i++) {
                System.out.println(fileList[i]);
            }
        }
        return fileList;
    }

}
