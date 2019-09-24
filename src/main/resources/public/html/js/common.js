var vm = new Vue({
  data() {
    return {
      num: 0,
      chartColor: ['#2ec7c9', '#b6a2de', '#5ab1ef', '#ffb980', '#d87a80', '#8d98b3', '#e5cf0d', '#97b552', '#95706d', '#dc69aa', '#07a2a4', '#9a7fd1', '#588dd5', '#f5994e', '#c05050', '#59678c', '#c9ab00', '#7eb00a', '#6f5553', '#c14089'],
    }
  },
  mounted() {
    //  this.num = localStorage.getItem('num') || 2;
  },
  methods: {
    //
    accAdd(arg1, arg2) {
      var r1, r2, m;
      try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 };
      try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 };
      m = Math.pow(10, Math.max(r1, r2)) * 10;
      var len = r1 > r2 ? r1 : r2;
      return parseFloat(((arg1 * m + arg2 * m) / m).toFixed(len));
    },
    treeFormat(data, props) {
      let id = props ? props.id : 'id';
      let pid = props ? props.pid : 'pid';
      data.forEach(function (item) {
        delete item.children;
      });
      var map = {};
      data.forEach(function (item) {
        map[item[id]] = item;
      });
      var val = [];
      data.forEach(function (item) {
        var parent = map[item[pid]];
        if (parent) {
          (parent.children || (parent.children = [])).unshift(item);
        } else {
          val.unshift(item);
        }
      });
      return val;
    },
    exportExcel(id, fileName) {
      var id = id || "excelTable";
      var wb;
      if ($("#" + id + " .el-table__fixed").length != 0) {
        wb = XLSX.utils.table_to_book(document.querySelector("#" + id + " .el-table__fixed"));
      } else {
        wb = XLSX.utils.table_to_book(document.querySelector("#" + id));
      }
      excel(wb, fileName)
    },
    boardScroll() {
    	setTimeout(function () {
            var winH = $("#app").height(),
              pageH = $(".board").height();
            if (winH < pageH) {
              setInterval(function () {
                $("#app").scrollTop($("#app").scrollTop() + 1)
                if ($("#app").scrollTop() >= (pageH - winH)) {
                  setTimeout(function () {
                    $("#app").scrollTop(0)
                  }, 3000)
                }
              }, 20)
            }
          }, 8000)
    },
  }
})



function getQueryVariable(variable) {
  var query = window.location.search.substring(1);
  var vars = query.split("&");
  for (var i = 0; i < vars.length; i++) {
    var pair = vars[i].split("=");
    if (pair[0] == variable) { return pair[1]; }
  }
  return (false);
}

function formatDate(date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
  }
  let o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  };
  for (let k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      let str = o[k] + '';
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
    }
  }
  return fmt;
};

function getDateFormat(date, format) {
  var o = {
    "M+": date.getMonth() + 1, //month 
    "d+": date.getDate(), //day 
    "H+": date.getHours(), //hour 
    "m+": date.getMinutes(), //minute 
    "s+": date.getSeconds(), //second 
    "q+": Math.floor((date.getMonth() + 3) / 3), //quarter 
    "S": date.getMilliseconds() //millisecond 
  }
  if (/(y+)/i.test(format)) {
    format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
  }
  for (var k in o) {
    if (new RegExp("(" + k + ")").test(format)) {
      format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    }
  }
  return format;
};

const getFullPathOfTree = (tree, id, path) => {
  if (!path) path = [];
  let node = getNodeOfTree(tree, id);
  if (node) {
    path.unshift(node);
    let parent = getParentNodeOfTree(tree, id);
    if (parent) {
      getFullPathOfTree(tree, parent.id, path);
    }
  }
  return path;
};

//Excel导出
function excel(wb, name) {
  // var FileSaver = require('file-saver');
  /* generate workbook object from table */

  // wb.Sheets.Sheet1.A1 = {width:20}
  /* get binary string as output */
  let wbout = XLSX.write(wb, { bookType: 'xlsx', bookSST: true, type: 'array' });
  console.log(wbout)
  try {

    saveAs(new Blob([wbout], { type: 'application/octet-stream' }), name + '.xlsx');
  } catch (e) {
    if (typeof console !== 'undefined')
      console.log(e, wbout)
  }
  return wbout
};
/**
 * 获取计算后的日期
 * 
 * @param count 
 * @param time 指定时间
 * @returns
 */
function getMonthCalculateByTime(count, time) {
  var date = new Date(time);
  var year = date.getFullYear();
  var month = date.getMonth() + 1;
  month += count;
  year += parseInt(month / 12);
  month %= 12;
  if (month < 1) {
    year--;
    month = 12 + month;
  }
  var dd = year + "-" + ((month < 10) ? ("0" + month) : month);
  if (time.length == 4) {
    return dd.substring(0, 4);
  }
  return dd;
}
/** 
 * 获取指定的URL参数值 
 * URL:http://www.quwan.com/index?name=tyler 
 * 参数：paramName URL参数 
 * 调用方法:getParam("name") 
 * 返回值:tyler 
 */
function getParam(paramName) {
  paramValue = "", isFound = !1;
  if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {
    arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;
    while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 && arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() && (paramValue = arrSource[i].split("=")[1], isFound = !0), i++
  }
  return paramValue == "" && (paramValue = null), paramValue
}

Array.prototype.getNumArrayTotal = function (num) {
  if (num <= 0) {
    return 0;
  }
  var total = this.reduce(function (pre, cur, index, arr) {
    if (index > num - 1) {
      return pre + 0;
    }
    return pre + cur;
  });
  return total;
};