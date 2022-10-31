import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

public class Test {
    //构造函数,用于显示三维图形
    public Test() {
        SimpleUniverse  universe=new SimpleUniverse();
        BranchGroup group=new BranchGroup();
        Sphere sphere=new Sphere(.5f);

        Color3f lightColor=new Color3f(1.8f,0.1f,0.1f);
        //作用范围
        BoundingSphere boundingSphere=new BoundingSphere(new Point3d(0.0,0.0,0.0),100.0);
        Vector3f light1Direction=new Vector3f(4.0f,-7.0f,-12.0f);
        DirectionalLight light1=new DirectionalLight(lightColor,light1Direction);
        light1.setInfluencingBounds(boundingSphere);
        group.addChild(light1);
        universe.getViewingPlatform().setNominalViewingTransform();





        group.addChild(sphere);
        universe.addBranchGraph(group);
    }

    public static void main(String[] args) {
        new Test();
    }
}
