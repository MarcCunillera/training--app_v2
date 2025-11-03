package com.rgb.training.app.boundary.soap;

import com.rgb.training.app.boundary.soap.parm.MyTableList;
import com.rgb.training.app.data.model.MyTable;
import com.rgb.training.app.data.repository.MyTableJTARepository;
import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.util.List;

/**
 *
 * @author LuisCarlosGonzalez
 */
@WebService(name = "MyTableRepository", serviceName = "MyTableRepositoryService")
public class MyTableRepositoryService {

    @Inject
    private MyTableJTARepository myTableRepo;

    @WebMethod(operationName = "getById")
    public MyTable getById(@WebParam(name = "id") Long id) {
        return myTableRepo.get(id);
    }

    @WebMethod
    public MyTableList getAllMyTable() {
        MyTableList response = new MyTableList();
        List<MyTable> results = myTableRepo.getAll();
        response.setValues(results);
        return response;
    }

    @WebMethod
    public MyTable addMyTable(MyTable newEntry) {
        MyTable result = myTableRepo.create(newEntry);
        if (result == null) {
            result = new MyTable();
        }
        return myTableRepo.create(result);
    }

    @WebMethod
    public MyTable updateMyTable(MyTable updatedEntry) {
        if (myTableRepo.get(updatedEntry.getId()) != null) {
            MyTable result = myTableRepo.update(updatedEntry);
            return result;
        }
        return null;
    }

    @WebMethod
    public Long deleteMyTable(Long id) {
        return myTableRepo.delete(id);
    }

    @WebMethod
    public String dummy(@WebParam(header = true) SecurityHeader secHeader) {
        return "dummy";
    }
}
