package uo.ri.cws.application.service.common;

import java.util.*;

public class TestContext {

    private final Map<Key, Object> table = new HashMap<>();
    private Exception exception;
    private Optional<?> optional;
    private List<?> resultList = new ArrayList<>();

    public TestContext put(Key key, Object value) {
        table.put(key, value);
        return this;
    }

    //
//	public TestContext clear() {
//		table.clear();
//		return this;
//	}
//
    public Object get(Key key) {
        return table.get(key);
    }

    public Exception getException() {

        return exception;
    }

    public void setException(Exception ex) {
        this.exception = ex;
    }

    public Optional<?> getUniqueResult() {
        return optional;
    }

    public void setUniqueResult(Optional<?> optional) {
        this.optional = optional;
    }

    public List<?> getResultList() {
        return resultList;
    }

    public void setResultList(List<?> list) {
        this.resultList = list;
    }

    public enum Key {
        MECHANIC, MECHANICS, CONTRACT, CONTRACTS, INFORCE, TERMINATED, PAYROLLS,
        PAYROLL, PROFESSIONALGROUP, CONTRACTTYPE, ACLIENT, WORKORDERS
    }
}
