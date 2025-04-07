package paypal;

import java.util.*;

/**
 * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
 *
 * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
 * Return true if you can finish all courses. Otherwise, return false.
 *
 *
 *
 * Example 1:
 *
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0. So it is possible.
 * Example 2:
 *
 * Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
 * Output: false
 * Explanation: There are a total of 2 courses to take.
 * To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
 *
 *
 * Constraints:
 *
 * 1 <= numCourses <= 2000
 * 0 <= prerequisites.length <= 5000
 * prerequisites[i].length == 2
 * 0 <= ai, bi < numCourses
 * All the pairs prerequisites[i] are unique.
 */
public class CourseSchedule {

    public static void main(String[] args){
        int[][] prerequisties = {{1,0}};
        System.out.println("can Finish: "+ canFinish(2,prerequisties));
    }

    public static boolean canFinish(int numCourses, int[][] prerequisites) {

        //create adjacency list with preReq and children as courses depending on the preeReq
        // 0 -> 1
        // also calculate indegree such as, each course depends on how many pre reqs.
        // [1,0] as course 0 depends on course 1 indegree is 1, as course 1 doesn't have any preReq it's indegree is 0.
        HashMap<Integer, List<Integer>> adjList = new HashMap<>();
        int[] indegree = new int[numCourses];
        for (int[] dep : prerequisites) {
            int course = dep[0];
            int preReq = dep[1];
            List<Integer> adj = adjList.getOrDefault(preReq, new ArrayList<>());
            adj.add(course);
            adjList.put(preReq, adj);
            indegree[course] += 1;
        }

        Queue<Integer> courseList = new LinkedList<>();
        //push all courses with indegree 0. i.e. those courses can be started, they don't depend on any preReq.
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                courseList.add(i);
            }

        }
        List<Integer> coursesDone = new ArrayList<>();
        // pop courses and update indegrees, if any child's indegree is 0, push to queue and add to coursesDone list.
        while (!courseList.isEmpty()) {
            Integer currentCourse = courseList.poll();
            coursesDone.add(currentCourse);
            if(adjList.containsKey(currentCourse)){
                for (Integer dep : adjList.get(currentCourse)) {
                    indegree[dep] -= 1;
                    if (indegree[dep] == 0) {
                        courseList.offer(dep);
                    }
                }
            }
        }

        //checking if all courses are done or not.
        return numCourses == coursesDone.size();


    }
}
