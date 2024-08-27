package com.ilot.utils.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(alphabetic = true)
public class _FileDto {

    protected Long            id;
    protected String[]        extensions;
    protected String          fileBase64;
    protected String          extension;
    protected String          fileName;
    protected String          fileUrl;
    protected MultipartFile   multiPartFile;
    protected Integer         user;
    protected String          fileNameWithoutExt;
    protected Boolean         isNotRenamed;
    protected String          size;
    protected String          version;
    protected String          rule;
    protected String          ruleCategoryCode;
    protected String          ruleCategoryLibelle;
    protected Boolean         drlFileSaving;
    protected Boolean         savedWithExtension;
    protected String          tenantCode;
    protected String          fullPath;
    protected MultipartFile   fileToUpload;
    protected MultipartFile[] filesToUpload;
    protected String[]        fileNames;
    protected String          userIds;
    protected Integer         userId;
    protected Boolean         isUpdate;
}
