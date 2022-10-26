package mastermind.ArrayReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


@SpringBootApplication
@RestController 
public class ArrayReaderApplication {

      public static void main(String[] args) {
            SpringApplication.run(ArrayReaderApplication.class, args);}

      public String ReadJSON() throws IOException, ParseException {
            JSONParser parcer = new JSONParser();
            Object obj = parcer.parse(new FileReader("C:\\data.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String path = (String) jsonObject.get("file_path");
            return path;
      }

      public ArrayList<Integer> ReadArray() {
            Scanner s = null;
            try {
                  s = new Scanner(new FileReader(ReadJSON())).useDelimiter("\n");
            } catch (IOException | ParseException e) {
                  throw new RuntimeException(e);
            }
            ArrayList<Integer> array = new ArrayList<>();
            while (s.hasNext()) {
                  array.add(Integer.valueOf(s.nextLine()));
            }
            return array;
      }

      @GetMapping("/get_max_value")
      public Object maxValue() {
            JSONObject jsonobject = new JSONObject();
            ArrayList<Integer> MyArray = ReadArray();
            Collections.sort(MyArray);
            jsonobject.put("max_value",MyArray.get(MyArray.size() - 1));
            return jsonobject;
      }

      @GetMapping("/get_min_value")
      public Object minValue() {
            JSONObject jsonobject = new JSONObject();
            ArrayList<Integer> MyArray = ReadArray();
            Collections.sort(MyArray);
            jsonobject.put("min_value",MyArray.get(0));
            return jsonobject;
      }

      @GetMapping("/mean")
      public Object mean() {
            JSONObject jsonobject = new JSONObject();
            ArrayList<Integer> MyArray = ReadArray();
            float sum = 0;
            for (int i = 0; i < MyArray.size(); i++)
                  sum += MyArray.get(i);
            jsonobject.put("mean",Math.round(sum/(float)MyArray.size()));
            return jsonobject;
      }

      @GetMapping("/median")
      public Object Median() {
            JSONObject jsonobject = new JSONObject();
            ArrayList<Integer> MyArray = ReadArray();
            float median = 0;
            if (MyArray.size() % 2 == 0) {
                  median = MyArray.get((MyArray.size() / 2));

            } else {
                  median = ((float) MyArray.get(Math.round(((float) MyArray.size()) / 2) - 2) + ((float) MyArray.get(Math.round(((float) MyArray.size()) / 2) - 1))) / 2;


            }
            jsonobject.put("median", median);
            return jsonobject;
      }

      @GetMapping("/increasing")
      public Object increasing() {
            JSONObject jsonobject = new JSONObject();
            int max = 0;
            ArrayList<Integer> MyArray = ReadArray();
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < MyArray.size(); i++) {
                  tmp.add(0);
            }

            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 1; i < MyArray.size(); i++) {
                  if (MyArray.get(i) > MyArray.get(i - 1)) {
                        tmp.set(i, tmp.get(i - 1) + 1);
                  } else tmp.set(i, 0);
                  {
                        max = Math.max(tmp.get(i), max);
                  }
            }
            for (int i = 0; i < MyArray.size(); i++) {
                  if (tmp.get(i) == max) {
                        for (int j = i - tmp.get(i); j <= i; j++) {
                              result.add(MyArray.get(j));
                        }
                  }
            }
            jsonobject.put("the longest increasing array slice",result);
            return jsonobject;
      }

      @GetMapping("/decreasing")
      public Object decreasing() {
            JSONObject jsonobject = new JSONObject();
            int max = 0;
            ArrayList<Integer> MyArray = ReadArray();
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < MyArray.size(); i++) {
                  tmp.add(0);
            }
            ArrayList<Integer> result = new ArrayList<>();
            for (int i = 1; i < MyArray.size(); i++) {
                  if (MyArray.get(i) < MyArray.get(i - 1)) {
                        tmp.set(i, tmp.get(i - 1) + 1);
                  } else tmp.set(i, 0);
                  {
                        max = Math.max(tmp.get(i), max);
                  }
            }
            for (int i = 0; i < MyArray.size(); i++) {
                  if (tmp.get(i) == max) {
                        for (int j = i - tmp.get(i); j <= i; j++) {
                              result.add(MyArray.get(j));
                        }

                  }
            }
            jsonobject.put("the longest decreasing array slice",result);
            return jsonobject;
      }
}
