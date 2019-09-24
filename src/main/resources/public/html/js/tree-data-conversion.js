function treeObj(originObj,flag){
	//对象深拷贝
	let obj ={};
	for (key in originObj){
		var val = originObj[key];
  		obj[key] = typeof val === 'object' ? arguments.callee(val):val;
   }

   if(flag==true)
   {
       //对象新增children键值，用于存放子树
       obj['children'] = [];
   }
   else
   { 
      obj['children'] = null;
   }
   return obj;
}
 
//data：带转换成树形结构的对象数组
//attributes：对象属性
function toTreeData (data, attributes) {
  let resData = data;
  let tree = [];
    
   

 //找寻根节点
  for (let i = 0; i < resData.length; i++) {
 
    if (resData[i][attributes.parentId] == '0') {
       
       var ischildren = isChild(resData[i]);
         // console.log(resData[i]);
          tree.push( treeObj(resData[i],ischildren) );
          resData.splice(i, 1);
          i--;
       
    }
  }
 
  run(tree);
 
  //找寻子树
  function run(chiArr) {
  
    if(chiArr==null)
     return ;

    if (resData.length !== 0) {
      for (let i = 0; i < chiArr.length; i++) {
        for (let j = 0; j < resData.length; j++) {
           
          if (chiArr[i][attributes.id] === resData[j][attributes.parentId]){
           
                var ischildren = isChild( resData[j] );

                let obj = treeObj(resData[j],ischildren);
                chiArr[i].children.push(obj);
                resData.splice(j, 1);
                j--;
 

          }
        }
        run(chiArr[i].children);
      }
    }
  }
 
  function isChild(node) {
      
     var flag=false;
     for (let j = 0; j < resData.length; j++) {
       
        if(node[attributes.id]==resData[j][attributes.parentId])
        {  
            flag=true;
            //console.log("true"); 
            break;
        }
        
     }
      
      return flag;

  }


  return tree;
 
}