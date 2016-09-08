package net.dontdrinkandroot.persistence.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
public class GeneratedLongIdUuidEntity extends GeneratedLongIdEntity implements UuidEntity<Long>
{
    @Column(length = 36, nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Override
    public String getUuid()
    {
        return this.uuid;
    }
}
