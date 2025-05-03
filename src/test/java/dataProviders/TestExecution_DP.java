package dataProviders;

import framework.PathUtils;
import framework.ReusableLibrary;
import framework.constants.AppConstants;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestExecution_DP extends ReusableLibrary {


    @DataProvider(name = "testDataExecutions")
    public Iterator<Map<String,String>> getTestData(Method m)
    {
        List<Map<String,String>> data=excelUtils.readCompleteDataFromExcel(PathUtils.getTestDataFolder()+"Sample_Executions.xlsx", AppConstants.INDEX_SHEET);

        System.out.println(m.getName());

        String sheetName=data.stream().filter(s->(s.get("Test Case Name").equalsIgnoreCase(m.getName()) && s.get("Run Mode").equalsIgnoreCase("Y"))).map(s->s.get("Sheet Name")).collect(Collectors.joining());

        if(sheetName.isBlank())
        {
            return new ArrayList<Map<String,String>>().iterator();
        }

        else {
            data = excelUtils.readCompleteDataFromExcel(PathUtils.getTestDataFolder() + "Sample_Executions.xlsx", sheetName);

            return data.iterator();
        }
    }

}
