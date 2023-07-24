package uo.ri.cws.domain.base;

import java.util.UUID;

public abstract class BaseEntity {

    private String id = UUID.randomUUID().toString();

    private long version;

    public BaseEntity() {
        super();
    }

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BaseEntity other = (BaseEntity) obj;
        if (id == null) {
            return other.id == null;
        } else {
            return id.equals(other.id);
        }
    }

}