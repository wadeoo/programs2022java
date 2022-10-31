import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class TwoPrimitivesDisplay{
    public BranchGroup createBranchGroup()
    {
        //定义BranchGroup
        BranchGroup BranchGroupRoot = new BranchGroup ();

        //创建球心在坐标系原点球形范围
        BoundingSphere bounds = new BoundingSphere(new Point3d( 0.0,0.0,0.0),100.0);

        //定义背景颜色
<<<<<<< HEAD
        Color3f bgColor=new Color3f(.5f,.0f,.5f);
=======
        Color3f bgColor=new Color3f(.0f,.0f,.0f);
>>>>>>> 98a5070 (1)
        Background bg=new Background(bgColor);
        bg.setApplicationBounds(bounds);
        BranchGroupRoot.addChild(bg);

        //定义平行光、颜色、照射方向与作用范围
        Color3f directionColor=new Color3f(1.f,1.f,1.f);
        Vector3f vec=new Vector3f(1.0f,-1.0f,-1.0f);
        DirectionalLight directionalLight= new DirectionalLight(directionColor,vec);
        directionalLight.setInfluencingBounds(bounds);
        BranchGroupRoot.addChild(directionalLight);

        //定义总的TransformGroup:transformgroup
        TransformGroup transformgroup=new TransformGroup();

        //设置对该TransformGroup的读写能力
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformgroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        //将该TransformGroup加入到BranchGroupRoot中
        BranchGroupRoot.addChild(transformgroup);

        //定义鼠标对场景的旋转、平移与放大功能
        MouseRotate mouserotate=new MouseRotate();
        mouserotate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mouserotate);
        mouserotate.setSchedulingBounds(bounds);

        MouseZoom mousezoom=new MouseZoom();
        mousezoom.setSchedulingBounds(bounds);
        BranchGroupRoot.addChild(mousezoom);
        mousezoom.setSchedulingBounds(bounds);

        MouseTranslate mousetranslate=new MouseTranslate();
        mousetranslate.setTransformGroup(transformgroup);
        BranchGroupRoot.addChild(mousetranslate);
        mousetranslate.setSchedulingBounds(bounds);

        //定义两个三维型体的外观
        Appearance app1=new Appearance();
        Material material1=new Material();
<<<<<<< HEAD
        material1.setDiffuseColor(new Color3f(.2f,.0f,0.5f));
=======
        material1.setDiffuseColor(new Color3f(1.0f,.0f,0.0f));
>>>>>>> 98a5070 (1)
        app1.setMaterial(material1);

        Appearance app2=new Appearance();
        Material material2=new Material();
<<<<<<< HEAD
        material2.setDiffuseColor(new Color3f(0.6f,.4f,0.0f));
=======
        material2.setDiffuseColor(new Color3f(0.0f,1.0f,0.0f));
>>>>>>> 98a5070 (1)
        app2.setMaterial(material2);

        //定义一个球体于一个长方体的大小、外观属性与坐标变换，并定义相应的TransformGroup：ta1、ta2
        TransformGroup tg1=new TransformGroup();
        tg1.addChild(new Sphere(0.3f,app1));
        Transform3D t=new Transform3D();
        t.setTranslation(new Vector3f(0.f,-0.425f,0.f));
        TransformGroup tg2=new TransformGroup(t);
        tg2.addChild(new Box(0.5f,0.05f,0.5f,app2));

        //将定义好的两个TransformGroup(tg1、tg2)加入到总的transformgroup
        transformgroup.addChild(tg1);
        transformgroup.addChild(tg2);

        //对BranchGroupRoot预编译
        BranchGroupRoot.compile();

        //通过方法名返回BranchGroupRoot
        return BranchGroupRoot;
    }
    public TwoPrimitivesDisplay()
    {
        SimpleUniverse universe = new SimpleUniverse();
        //设置SimpleUniverse，由系统选择视点在z轴的正向，观察方向沿z轴反向
        BranchGroup BranchGroupScene=createBranchGroup();
        universe.getViewingPlatform().setNominalViewingTransform();
        // 把group加入到虚拟空间中
        universe.addBranchGraph(BranchGroupScene);
    }

    public static void main(String[] args)
    {//通过MainFrame显示图像

        new TwoPrimitivesDisplay();

    }
}

