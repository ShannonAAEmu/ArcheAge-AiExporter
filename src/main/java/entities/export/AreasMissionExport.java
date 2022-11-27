package entities.export;

import com.google.gson.annotations.Expose;
import missions.impl.AIShape;
import missions.impl.PolygonArea;
import missions.impl.SpecialArea;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AreasMissionExport {

    @Expose
    private final List<List<AIShape>> forbiddenAreasList;

    @Expose
    private final List<List<SpecialArea>> navigationModifiersList;

    @Expose
    private final List<List<AIShape>> designerForbiddenAreasList;

    @Expose
    private final List<List<AIShape>> forbiddenBoundariesList;

    @Expose
    private final List<List<AIShape>> extraLinkCostsList;

    @Expose
    private final List<List<PolygonArea>> designerPathsList;

    public AreasMissionExport() {
        this.forbiddenAreasList = new ArrayList<>();
        this.navigationModifiersList = new ArrayList<>();
        this.designerForbiddenAreasList = new ArrayList<>();
        this.forbiddenBoundariesList = new ArrayList<>();
        this.extraLinkCostsList = new ArrayList<>();
        this.designerPathsList = new ArrayList<>();
    }

    public List<List<SpecialArea>> getNavigationModifiersList() {
        return navigationModifiersList;
    }

    public List<List<AIShape>> getDesignerForbiddenAreasList() {
        return designerForbiddenAreasList;
    }

    public List<List<AIShape>> getForbiddenBoundariesList() {
        return forbiddenBoundariesList;
    }

    public List<List<AIShape>> getExtraLinkCostsList() {
        return extraLinkCostsList;
    }

    public List<List<PolygonArea>> getDesignerPathsList() {
        return designerPathsList;
    }

    public List<List<AIShape>> getForbiddenAreasList() {
        return forbiddenAreasList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreasMissionExport that = (AreasMissionExport) o;
        return Objects.equals(forbiddenAreasList, that.forbiddenAreasList) && Objects.equals(navigationModifiersList, that.navigationModifiersList) && Objects.equals(designerForbiddenAreasList, that.designerForbiddenAreasList) && Objects.equals(forbiddenBoundariesList, that.forbiddenBoundariesList) && Objects.equals(extraLinkCostsList, that.extraLinkCostsList) && Objects.equals(designerPathsList, that.designerPathsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forbiddenAreasList, navigationModifiersList, designerForbiddenAreasList, forbiddenBoundariesList, extraLinkCostsList, designerPathsList);
    }

    @Override
    public String toString() {
        return "AreasMissionExport{" +
                "forbiddenAreasList=" + forbiddenAreasList +
                ", navigationModifiersList=" + navigationModifiersList +
                ", designerForbiddenAreasList=" + designerForbiddenAreasList +
                ", forbiddenBoundariesList=" + forbiddenBoundariesList +
                ", extraLinkCostsList=" + extraLinkCostsList +
                ", designerPathsList=" + designerPathsList +
                '}';
    }
}
