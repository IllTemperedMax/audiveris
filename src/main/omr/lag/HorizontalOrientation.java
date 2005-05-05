//-----------------------------------------------------------------------//
//                                                                       //
//               H o r i z o n t a l O r i e n t a t i o n               //
//                                                                       //
//  Copyright (C) Herve Bitteur 2000-2005. All rights reserved.          //
//  This software is released under the terms of the GNU General Public  //
//  License. Please contact the author at herve.bitteur@laposte.net      //
//  to report bugs & suggestions.                                        //
//-----------------------------------------------------------------------//
//      $Id$
package omr.lag;

import java.awt.Point;
import java.awt.Rectangle;

/**
 * Class <code>HorizontalOrientation</code> defines an orientation where
 * sections are horizontal (coord is x, pos is y)
 */
public class HorizontalOrientation
    implements Oriented,
               java.io.Serializable
{

    //------------//
    // isVertical //
    //------------//
    public boolean isVertical ()
    {
        return false;
    }

    //-----------//
    // switchRef //
    //-----------//
    public Point switchRef (Point cp,
                            Point xy)
    {
        if (xy == null) {
            xy = new Point();
        }

        // Horizontal: coord->x, pos->y
        xy.x = cp.x;
        xy.y = cp.y;

        return xy;
    }

    //-----------//
    // switchRef //
    //-----------//
    public Rectangle switchRef (Rectangle cplt,
                                Rectangle xywh)
    {
        if (xywh == null) {
            xywh = new Rectangle();
        }

        // Horizontal: coord->x, pos->y, length->width, thickness->height
        xywh.x = cplt.x;
        xywh.y = cplt.y;
        xywh.width = cplt.width;
        xywh.height = cplt.height;

        return xywh;
    }
}
