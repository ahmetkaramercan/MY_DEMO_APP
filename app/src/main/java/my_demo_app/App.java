/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package my_demo_app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;
import static spark.Spark.port;

public class App {
    public String getGreeting() {
        return "Welcome to my project please go to copmute page.";
    }
    
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        /*int port = Integer.parseInt(System.getenv("PORT"));
        port(port);*/
        

        get("/", (req, res) -> "Welcome to my project please go to copmute page.");

        post("/compute", (req, res) -> {

            String input1 = req.queryParams("input1");
            java.util.Scanner sc1 = new java.util.Scanner(input1);
            sc1.useDelimiter("[;\r\n]+");
            java.util.ArrayList<Integer> inputList = new java.util.ArrayList<>();
            while (sc1.hasNext())
            {
                int value = Integer.parseInt(sc1.next().replaceAll("\\s",""));
                inputList.add(value);
            }
            sc1.close();
            System.out.println(inputList);


            String input2 = req.queryParams("input2").replaceAll("\\s","");
            Integer input2AsInt = Integer.parseInt(input2);

            String input3 = req.queryParams("input3").replaceAll("\\s","");
            Integer input3AsInt = Integer.parseInt(input3);

            boolean result = App.checkSubarraySum(inputList, input2AsInt, input3AsInt);
            System.out.println(result);
            Map<String, Boolean> map = new HashMap<String, Boolean>();
            
            map.put("result", result);
            return new ModelAndView(map, "compute.mustache");
        }, new MustacheTemplateEngine());


        get("/compute",
            (rq, rs) -> {
              Map<String, String> map = new HashMap<String, String>();
              map.put("result", "not computed yet!");
              return new ModelAndView(map, "compute.mustache");
            },
            new MustacheTemplateEngine());
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    static boolean checkSubarraySum(ArrayList<Integer> array, Integer k, Integer sum)
    {
        if (array == null) return false;

        int n = array.size();
        for (int i = 0; i < n - k + 1; i++)
        {
            int current_sum = 0;

            for (int j = 0; j < k; j++)
                current_sum = current_sum + array.get(i + j);
    
            if (current_sum == sum)
                return true;    
        }
        return false;
    }
}
