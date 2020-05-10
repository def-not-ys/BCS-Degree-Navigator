package ui.coursedata;


import model.Course;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

// represents a website scraper that scrapers course data from ubc academic calendar
// reference: https://www.javatpoint.com/jsoup-tutorial
//            https://jsoup.org/
//            https://javarevisited.blogspot.com/2014/09/how-to-parse-html-file-in-java-jsoup-example.html
public class CourseWebScraper {

    HashMap<String, Course> courses;

    // EFFECTS: constructs a web scraper
    public CourseWebScraper(String url) throws IOException {
        courses = new HashMap<>();
        scrapCourses(url);
    }

    // REQUIRES: valid url, website format as UBC course description
    // MODIFIES: this
    // EFFECTS: create a list of courses from given course website url
    private void scrapCourses(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements titles = doc.body().getElementsByTag("dt");
        Elements descriptions = doc.body().getElementsByTag("dd");

        int n = titles.size();

        for (int i = 0; i < n; i++) {
            String[] ss = titles.get(i).text().split(" ");
            String fn = ss[0];
            String cnum = ss[1];
            String cid = fn + " " + cnum;
            String cnm = "";
            int credit = Integer.parseInt(ss[2].substring(1,2));
            for (int k = 3; k < ss.length; k++) {
                cnm = cnm + ss[k] + " ";
            }
            String cdes = "description not available";
            Course course = new Course(cnm, cnum, fn, cdes, credit);

            if (descriptions.size() >= i) {
                //processDescription(i, course, descriptions.get(i));
                course.setCourseDescription(descriptions.get(i).text());
            }
            courses.put(cid, course);
        }
    }

//    // REQUIRES:
//    // MODIFIES: course
//    // EFFECTS: extracts course pre-reqs from the course description
//    private void processDescription(int index, Course course, Element dd) {
//        //Document description = (Document)dd;
//        String reqs = description.body().getElementsByTag("br").text();
//        if (reqs.length() == 0) {
//            String str = description.text();
//            course.setCourseDescription(str);
//        } else {
//            course.setCourseDescription(description.title());
//            Elements titles = description.body().getElementsByTag("em");
//            for (Element title: titles) {
//                if (title.text().equalsIgnoreCase("Equivalency:")) {
//                    course.setEquivalency(new ArrayList<>()); // stub
//                } else {
//                    course.setPreReqs(new ArrayList<>()); // stub
//                }
//            }
//        }
//    }

    public HashMap<String, Course> getCourses() {
        return courses;
    }
}
