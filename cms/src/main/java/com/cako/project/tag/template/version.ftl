<div class="row-fluid">
    <div class="span12">
        <script type="text/javascript">
            function deleteVersion(id){
                $('#queryTable tr').css({'cursor':'pointer'}).on('click', function() {
                    var id = $(this).attr('id');
                    if(id=='stop'){
                        return false;
                    }
                    $(this).remove();
                });
            }
            $(document).ready(function(){
               $("#btnSubmit").click(function(){
                   var file = document.getElementById("file");
                   $.ajaxFileUpload({
                       secureuri : false,
                       fileElementId : 'file',
                       dataType : 'json',
                       url : '${ctx}/version/upLoad/'+file,
                       success : function(data) {
                           alert(data);
                       }
                   });
               });
            });
        </script>
        <div class="span12" style="margin-left: 15px;margin-right: 20px;>
            <form enctype="multipart/form-data" action="${ctx}/version/upLoad/1" method="post">
                <input type="file" multiple="multiple" name="file" id="file1">
                <input type="file" multiple="multiple" name="file1" id="file2">
                <input type="button" value="添加上传文本框" id="add">
                <input type="button" id="btnSubmit" name="submit" value="上传文件"/>
            </form>
        </div>
        <table id="queryTable" class="table table-striped table-bordered table-hover" style="margin-left: 15px;margin-right: 20px;>
            <tr style="background:blue;" id="stop">
                <td style="text-align: center">表头1</td>
                <td style="text-align: center">表头2</td>
                <td style="text-align: center">表头3</td>
                <td style="text-align: center">表头4</td>
                <td style="text-align: center">表头5</td>
                <td style="text-align: center">操作</td>
            </tr>
            <tr id="1">
                <td style="text-align: center">表头11</td>
                <td style="text-align: center">表头22</td>
                <td style="text-align: center">表头33</td>
                <td style="text-align: center">表头44</td>
                <td style="text-align: center">表头55</td>
                <td style="text-align: center">
                    <a href="javascript:;" onclick="deleteVersion('1')" id="deleteVersion" class="small green" style="cursor: pointer">操作1</a>
                </td>
            </tr>
            <tr id="2">
                <td style="text-align: center">表头11</td>
                <td style="text-align: center">表头22</td>
                <td style="text-align: center">表头33</td>
                <td style="text-align: center">表头44</td>
                <td style="text-align: center">表头55</td>
                <td style="text-align: center">
                    <a href="javascript:;" onclick="deleteVersion('2')" id="deleteVersion" class="small green" style="cursor: pointer">操作2</a>
                </td>
            </tr>
            <tr id="3">
                <td style="text-align: center">表头11</td>
                <td style="text-align: center">表头22</td>
                <td style="text-align: center">表头33</td>
                <td style="text-align: center">表头44</td>
                <td style="text-align: center">表头55</td>
                <td style="text-align: center">
                    <a href="javascript:;" onclick="deleteVersion('3')" id="deleteVersion" class="small green" style="cursor: pointer">操作3</a>
                </td>
            </tr>
        </table>
    </div>
</div>