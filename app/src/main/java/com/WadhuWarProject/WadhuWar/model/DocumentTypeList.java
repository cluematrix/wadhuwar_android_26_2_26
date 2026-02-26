package com.WadhuWarProject.WadhuWar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DocumentTypeList {


    @SerializedName("DocumentTypeList")
    @Expose
    public ArrayList<DocumentTypeListData> documentTypeList;


    @SerializedName("resid")
    @Expose
    String resid;

    @SerializedName("resMsg")
    @Expose
    String resMsg;

    public ArrayList<DocumentTypeListData> getDocumentTypeList() {
        return documentTypeList;
    }

    public void setDocumentTypeList(ArrayList<DocumentTypeListData> documentTypeList) {
        this.documentTypeList = documentTypeList;
    }

    public String getResid() {
        return resid;
    }

    public void setResid(String resid) {
        this.resid = resid;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public static class DocumentTypeListData {


        @SerializedName("name")
        @Expose
        String name;





        public DocumentTypeListData( String name) {
            this.name = name;

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


    }

}
