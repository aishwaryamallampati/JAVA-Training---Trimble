package com.training.interfaces;

import com.training.constants.Constants;
import com.training.pointentity.Point;

public interface IApplicationActivity {
    void updatePointList();

    void selectPoint();

    void exportPoints();

    void moveToPointCreatorFragment();

    void moveToNewPointCreatorFragment();

    void moveToAttributeSelectionFragment(Point selectedPoint);

    void moveToRelPointCreatorFragment(Constants.Attribute pointAttrChosen);
}
