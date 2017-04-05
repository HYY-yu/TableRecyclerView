# TableRecylerView
LayoutManager实现的复杂表格控件

--- 
初步实现效果,欢迎issues和fork
--- 
![github](https://github.com/HYY-yu/TableRecylerView/blob/master/cat1.gif "show")

**自定义属性：** 

名称|格式|介绍
----|----|----
fixtable divider_color| format="color"| 表格分割线颜色
fixtable divider_height| format="dimension" |表格分割线高度
fixtable column-1_color" |format="color"  |每隔一行就绘制此颜色作为行背景
fixtable column-2_color" |format="color"  |每隔一行就绘制此颜色作为行背景
fixtable title_color" |format="color"  |标题背景色
fixtable item_width" |format="dimension" | item 宽  如果item显示不全，加大此值
fixtable item-top-bottom_padding" |format="dimension"  |可以控制item竖直方向的高度
fixtable item_gravity" |format="enum" |枚举值 LEFT CENTER RIGHT
fixtable show-left-view_shadow"| format="boolean" |是否显示leftView的阴影

**使用**
--- 
 在fixtablelayout的build下找到output文件夹, 将fixtablelayout.aar导入项目.
