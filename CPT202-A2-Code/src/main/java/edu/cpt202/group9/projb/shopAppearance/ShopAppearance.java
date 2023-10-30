package edu.cpt202.group9.projb.shopAppearance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "shopAppearance")
public class ShopAppearance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] imageData;

    private String base64Encoded;

    private String description;

    public ShopAppearance() {
    }

    public ShopAppearance(String fileName, String fileType, byte[] imageData, String base64Encoded, String description) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.imageData = imageData;
        this.base64Encoded = null;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ShopAppearance))
            return false;
        ShopAppearance other = (ShopAppearance) obj;
        return fileName.equals(other.fileName)
                && fileType.equals(other.fileType)
                && description.equals(other.description);
    }

    public String getBase64Encoded() {
        return base64Encoded;
    }

    public void setBase64Encoded(String base64Encoded) {
        this.base64Encoded = base64Encoded;
    }

}