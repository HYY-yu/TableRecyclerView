# TableRecylerView
LayoutManager实现的大表格控件

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
 
 
 
**License**
--- 
MIT License

Copyright (c) 2017 YUFENG

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
