package com.miedzic.shop.domain.dao;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.io.IOException;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Audited
@Table(indexes = @Index(name = "idx_name", columnList = "name", unique = true))
public class Product extends Auditable implements IdentifiedDataSerializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    @NotNull
    private Long cost;
    private String path;

    @Override
    public int getFactoryId() {
        return 1;
    }

    @Override
    public int getClassId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeLong(id);
        objectDataOutput.writeString(name);
        objectDataOutput.writeString(category);
        objectDataOutput.writeLong(cost);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        this.id = objectDataInput.readLong();
        this.name = objectDataInput.readString();
        this.category = objectDataInput.readString();
        this.cost = objectDataInput.readLong();
    }

}
