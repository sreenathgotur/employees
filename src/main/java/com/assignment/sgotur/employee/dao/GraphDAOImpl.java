package com.assignment.sgotur.employee.dao;

import java.util.ArrayList;

import java.util.List;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import static org.neo4j.driver.v1.Values.parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import com.assignment.sgotur.employee.entity.Employee;
import com.assignment.sgotur.employee.utils.GraphUtils;

@Repository
@Configuration
public class GraphDAOImpl {

	StatementResult result = null;
	Session session = null;
	List<Employee> employeeList = new ArrayList<Employee>();

	StringBuffer MATCH_QUERY = new StringBuffer("MATCH (n:Employees) RETURN n.name,n.empID LIMIT 10");
	StringBuffer CREATE_QUERY=new StringBuffer("CREATE(n:Employees) SET n.name=$name, n.empID=$empID return n.empID");
	
	@Autowired
	private GraphUtils graphUtils;
	

	public List<Employee> getEmployees() {

		Driver driver = graphUtils.getDriver();
		Session session = driver.session();
		try {
			result = session.run(MATCH_QUERY.toString());

			while (result.hasNext()) {
				System.out.println("Inside");
				Employee employee = new Employee();
				Record record = result.next();
				System.out.println("Name:" + record.get("n.name").toString());
				System.out.println("Id:" + Integer.parseInt(record.get("n.empID").toString().replace("\"", "")));
				employee.setName(record.get("n.name").toString().replace("\"", ""));
				employee.set_empID(Integer.parseInt(record.get("n.empID").toString().replace("\"", "")));
			
				employeeList.add(employee);
			}
		} catch (Exception exception) {
			System.out.println("Exception is " + exception);
		}
		finally {
			session.close();
		}

		return employeeList;
	}
	
	public Boolean createEmployee(String name, String empID) {
		Driver driver = graphUtils.getDriver();
		Session session = driver.session();
		Boolean trxResult=false;
		try {
			result = session.run(CREATE_QUERY.toString(),parameters("name",name, "empID",empID));
			trxResult=true;
		}
		catch(Exception exception) {
			System.out.println("Exception Occured:" + exception);
		}
		finally {
			session.close();
		}
		return trxResult;
	}
}
