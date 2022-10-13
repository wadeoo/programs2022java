package cn.edu.fzu.sm2020.shape;

import java.awt.*;
import java.io.Serializable;

public class Circle implements Serializable {
   public Circle() {
      this.point1 = null;
      this.point2 = null;
      this.color=Color.BLACK;
      this.width=1;
      this.radius=0;
      this.center=null;
   }

   public Point point1;
   public Point point2;
   public Color color;
   public int width;
   public int radius;
   public Point center;
}
