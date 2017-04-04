# TableRecylerView
LayoutManager实现的复杂表格控件

--- 
初步实现效果,有待修复BUG和进一步的封装
--- 
![github](https://github.com/HYY-yu/TableRecylerView/blob/master/cat1.gif "show")




---  
自定义属性：
<attr name="fixtable_divider_color" format="color"/>   表格分割线颜色
<attr name="fixtable_divider_height" format="dimension"/>  表格分割线高度
<attr name="fixtable_s_color" format="color"/>      每隔一行就绘制此颜色作为行背景
<attr name="fixtable_title_color" format="color"/>    标题背景色
<attr name="fixtable_item_width" format="dimension"/>   item 宽  如果item显示不全，加大此值
<attr name="fixtable_item_top_bottom_padding" format="dimension"/>   可以控制item竖直方向的高度
<attr name="fixtable_item_gravity" format="enum"> 
    <enum name="CENTER" value="0"/>
    <enum name="LEFT" value="1"/>
    <enum name="RIGHT" value="2"/>
</attr>
<attr name="fixtable_show_left_view_shadow" format="boolean"/>  是否显示leftView的阴影
