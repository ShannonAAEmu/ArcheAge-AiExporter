package entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class LinkDescriptor {

    @Expose
    private long sourceNode;

    @Expose
    private long targetNode;

    @Expose
    private Vector edgeCenter;

    @Expose
    private double maxPassRadius;

    @Expose
    private double exposure;

    @Expose
    private double length;

    @Expose
    private double maxWaterDepth;

    @Expose
    private double minWaterDepth;

    @Expose
    private byte startIndex;

    @Expose
    private byte endIndex;

    @Expose
    private boolean isPureTriangularLink;

    @Expose
    private boolean simplePassabilityCheck;

    public LinkDescriptor() {
    }

    public long getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(long sourceNode) {
        this.sourceNode = sourceNode;
    }

    public long getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(long targetNode) {
        this.targetNode = targetNode;
    }

    public Vector getEdgeCenter() {
        return edgeCenter;
    }

    public void setEdgeCenter(Vector edgeCenter) {
        this.edgeCenter = edgeCenter;
    }

    public double getMaxPassRadius() {
        return maxPassRadius;
    }

    public void setMaxPassRadius(double maxPassRadius) {
        this.maxPassRadius = maxPassRadius;
    }

    public double getExposure() {
        return exposure;
    }

    public void setExposure(double exposure) {
        this.exposure = exposure;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getMaxWaterDepth() {
        return maxWaterDepth;
    }

    public void setMaxWaterDepth(double maxWaterDepth) {
        this.maxWaterDepth = maxWaterDepth;
    }

    public double getMinWaterDepth() {
        return minWaterDepth;
    }

    public void setMinWaterDepth(double minWaterDepth) {
        this.minWaterDepth = minWaterDepth;
    }

    public byte getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(byte startIndex) {
        this.startIndex = startIndex;
    }

    public byte getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(byte endIndex) {
        this.endIndex = endIndex;
    }

    public boolean isPureTriangularLink() {
        return isPureTriangularLink;
    }

    public void setPureTriangularLink(boolean pureTriangularLink) {
        isPureTriangularLink = pureTriangularLink;
    }

    public boolean isSimplePassabilityCheck() {
        return simplePassabilityCheck;
    }

    public void setSimplePassabilityCheck(boolean simplePassabilityCheck) {
        this.simplePassabilityCheck = simplePassabilityCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkDescriptor that = (LinkDescriptor) o;
        return sourceNode == that.sourceNode && targetNode == that.targetNode && Double.compare(that.maxPassRadius, maxPassRadius) == 0 && Double.compare(that.exposure, exposure) == 0 && Double.compare(that.length, length) == 0 && Double.compare(that.maxWaterDepth, maxWaterDepth) == 0 && Double.compare(that.minWaterDepth, minWaterDepth) == 0 && startIndex == that.startIndex && endIndex == that.endIndex && isPureTriangularLink == that.isPureTriangularLink && simplePassabilityCheck == that.simplePassabilityCheck && Objects.equals(edgeCenter, that.edgeCenter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceNode, targetNode, edgeCenter, maxPassRadius, exposure, length, maxWaterDepth, minWaterDepth, startIndex, endIndex, isPureTriangularLink, simplePassabilityCheck);
    }

    @Override
    public String toString() {
        return "LinkDescriptor{" +
                "sourceNode=" + sourceNode +
                ", targetNode=" + targetNode +
                ", edgeCenter=" + edgeCenter +
                ", maxPassRadius=" + maxPassRadius +
                ", exposure=" + exposure +
                ", length=" + length +
                ", maxWaterDepth=" + maxWaterDepth +
                ", minWaterDepth=" + minWaterDepth +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", isPureTriangularLink=" + isPureTriangularLink +
                ", simplePassabilityCheck=" + simplePassabilityCheck +
                '}';
    }
}
