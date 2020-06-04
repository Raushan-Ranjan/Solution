import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import static java.time.temporal.ChronoUnit.DAYS;

public class Test {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        LinkedHashMap<String, Integer> lhm = new LinkedHashMap<>();   // to store Dictionary key-value pair

        String input = br.readLine();     //input is in the format 2019-01-01:200,2019-02-01:138
        String[] key_value = input.split(",");    // two input is seperated by ,

        for (String string : key_value) {
            String[] first = string.split(":");    //key-value is seperated by :
            int val = Integer.parseInt(first[1]);
            lhm.put(first[0], val);
        }

        LinkedHashMap<String, Integer> missingDate = solution(lhm);
        Set<String> result = missingDate.keySet();
        Iterator<String> itera = result.iterator();
        String dummy = itera.next();
        System.out.print(dummy + ":" + missingDate.get(dummy));
        while (itera.hasNext()) {
            dummy = itera.next();
            System.out.print("," + dummy + ":" + missingDate.get(dummy));

        }
    }

   static LinkedHashMap<String,Integer> solution(LinkedHashMap<String,Integer> lhm){
   
        LinkedHashMap<String,Integer> missingDate = new LinkedHashMap<>();    // taking another map to store final result
       Set<String> set = lhm.keySet();       //taking key into Set
        Iterator<String> it = set.iterator();
        String cur = it.next();
        LocalDate currDate = LocalDate.parse(cur);
        missingDate.put(currDate.toString(),lhm.get(cur));
        while(it.hasNext()){                                      //traversing through key of given input
            String nextDateString = it.next();
            LocalDate nextDate = LocalDate.parse(nextDateString);    // cast String to localDate
            long gapDays = DAYS.between(currDate,nextDate);            // Finding number of gap between two dates
            if(gapDays==1){
                missingDate.put(nextDate.toString(),lhm.get(nextDateString));
            }else{
                int curValue =  lhm.get(cur);
                int nextValue =  lhm.get(nextDateString);
                long avgValue = Math.abs(curValue-nextValue);
                long incre = avgValue/gapDays;
                curValue = Math.min(curValue,nextValue);
                int added= curValue + (int ) incre;
                while(gapDays-->0){

                    currDate = currDate.plusDays(1);
                    missingDate.put(currDate.toString(),added);
                    added+=incre;

                }
                missingDate.put(nextDate.toString(),nextValue);
            }
            cur=nextDateString;
            currDate=nextDate;

        }
          return missingDate;
        }
    }

