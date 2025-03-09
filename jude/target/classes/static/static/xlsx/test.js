                         function export1(){
         var exportData
         //导出方法
         var selectedRows=$("#dg").datagrid('getRows');
         var datas=[];
         var index;
         var size=Object.keys(selectedRows[0]).length
         //var size=15
         //selectedRows.length
         for(var i=0;i<selectedRows.length;i++){
           for(var j=0;j<size;j++){
              datas[i]=[]
              index=j+1
              datas[i][j] =selectedRows[i]['c'+index]
              console.log(selectedRows[i]['c'+index])
            }
         }
console.log(selectedRows)
         console.log(datas)
          //toExcel(outTitles, twoDArray, '案件导出.xlsx', '案件');

}