package fuzzy;
import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class FuzzyDriver {

    private static final String fileName = System.getProperty("user.dir") + "\\src\\main\\java\\fuzzy\\driver.fcl";

    public static double evalForce(int botDist, int topDist, double velocity){
        FIS fis = FIS.load(fileName, true);
        fis.setVariable("velocity", velocity);
        fis.setVariable("bottom_distance", botDist);
        fis.setVariable("top_distance", topDist);
        //JFuzzyChart.get().chart(fis);
        fis.evaluate();
        Variable res = fis.getVariable("acceleration");
        //JFuzzyChart.get().chart(res, res.getDefuzzifier(), true);
        return res.defuzzify();
    }

    public static void main(String[] args){
           System.out.println(evalForce(100,32,3));
    }

}
