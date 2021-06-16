package com.teamgreen.greenhouse.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.sql.Timestamp;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FormattedData {
    private long nodeId;
    private List<NodeData> nodeData;

    public FormattedData(long nodeId, List<NodeData> nodeData) {
        this.nodeId = nodeId;
        this.nodeData = nodeData;
    }

    public long getNodeId() {
        return nodeId;
    }

    public void setNodeId(long nodeId) {
        this.nodeId = nodeId;
    }

    public List<NodeData> getNodeData() {
        return nodeData;
    }

    public void setNodeData(List<NodeData> nodeData) {
        this.nodeData = nodeData;
    }

    public static class NodeData {
        private long sensorId;
        private String sensorName;
        private String measuringParameter;
        private List<Double> dataList;
        private List<Timestamp> dateList;

        public NodeData(long sensorId, String sensorName, String measuringParameter, List<Double> dataList, List<Timestamp> dateList) {
            this.sensorId = sensorId;
            this.sensorName = sensorName;
            this.measuringParameter = measuringParameter;
            this.dataList = dataList;
            this.dateList = dateList;
        }

        public long getSensorId() {
            return sensorId;
        }

        public void setSensorId(long sensorId) {
            this.sensorId = sensorId;
        }

        public String getSensorName() {
            return sensorName;
        }

        public void setSensorName(String sensorName) {
            this.sensorName = sensorName;
        }

        public String getMeasuringParameter() {
            return measuringParameter;
        }

        public void setMeasuringParameter(String measuringParameter) {
            this.measuringParameter = measuringParameter;
        }

        public List<Double> getDataList() {
            return dataList;
        }

        public void setDataList(List<Double> dataList) {
            this.dataList = dataList;
        }

        public List<Timestamp> getDateList() {
            return dateList;
        }

        public void setDateList(List<Timestamp> dateList) {
            this.dateList = dateList;
        }
    }
}
