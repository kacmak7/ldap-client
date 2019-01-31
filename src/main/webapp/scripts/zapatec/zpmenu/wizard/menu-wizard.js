/**
 * $Id: menu-wizard.js 5895 2007-01-08 18:15:48Z ken $
 * The Zapatec DHTML Menu Wizard
 *
 * Copyright (c) 2004-2006 by Zapatec, Inc.
 * http://www.zapatec.com
 * 1700 MLK Way, Berkeley, California,
 * 94709, U.S.A.
 * All rights reserved.
 *
 * $Id: menu-wizard.js 5895 2007-01-08 18:15:48Z ken $
 *
 * Menu Wizard
 */

function _el(id) {
  return document.getElementById(id);
};

var customClasses = null;
var iconWind = null;
var wizard = null;
var zpDesignCurrentLi = null; // global variable to hold the current node in design mode
var zpDesignCurrentDiv = null;
var contentsMenu = null;
var strCurrentThemeName = 'office_blue';

function initWizard() {
  customClasses = new CustomClasses();
  iconWind = new IconWind();
  wizard = new Zapatec.Wizard({
      tabsID      : 'tabs',
      tabBarID    : 'tab-bar'
    });
  wizard.onInit = initPage;
  wizard.onBeforeTabChange = function(currentTab, newTab) {
    if (newTab != "Introduction") {
      paneContents();
    }
    // allow tab to change
    return true;
  };
  wizard.setupNav();
  wizard.init();
  //Finished Loading Make the wizard visible
  _el("tabs").style.visibility = 'visible';
  _el('loading').style.display = 'none';
  _el('content').style.display = 'block';

//  alert(((new Date).getTime() - tick) / 1000); // this will show loading time in seconds
};

function initPage() {
  Zapatec.Tooltip.setupFromDFN(); //setup the tooltips
  var designlabel = _el("f_design_label");
  designlabel.onkeyup = function() {
    var self = this;
    setTimeout(function(){designChangeCurrentLabel(self.value)}, 25);
  };
  designlabel.onkeypress = function(ev) {
    ev || (ev = window.event);
    if (ev.keyCode == 13) {
      designInsert(true);
    }
  };
  var designlink = _el("f_design_link");
  designlink.onkeyup = function() {
    var self = this;
    setTimeout(function(){designChangeCurrentLink(self.value)}, 25);
  };
  var designtarget = _el("f_design_target");
  designtarget.onkeyup = function() {
    var self = this;
    setTimeout(function(){designChangeCurrentTarget(self.value)}, 25);
  };
};

/**
 * Contents pane
 */

function designChangeCurrentLabel(text) {
  if (zpDesignCurrentLi && zpDesignCurrentDiv && zpDesignCurrentDiv.__zp_label) {
    // remove current text and get href
    var href = _el("f_design_link").value, target = _el("f_design_target").value;
    for (var i = 0; i < zpDesignCurrentLi.childNodes.length; i++) {
      var child = zpDesignCurrentLi.childNodes[i];
      if (child.nodeType == 1) { // ELEMENT_NODE
        if (child.tagName.toLowerCase() == 'a') {
          href = child.getAttribute('href');
          target = child.getAttribute('target');
          zpDesignCurrentLi.removeChild(child);
        }
      } else if (child.nodeType == 3) { // TEXT_NODE
        zpDesignCurrentLi.removeChild(child);
      }
    }
    while (zpDesignCurrentDiv.__zp_label.firstChild) {
      zpDesignCurrentDiv.__zp_label.removeChild(zpDesignCurrentDiv.__zp_label.firstChild);
    }
    // insert new text
    text = text.replace(/^\s+/, '').replace(/\s+$/, '');
    designChangeCurrentLi(text, href, target);
    // Redraw submenu because item width and height may be changed
    contentsMenu.refreshSubMenu();
    ContentsMenu.setActiveItem(zpDesignCurrentDiv); // Resize active item border
  }
};

function designChangeCurrentLink(href) {
  if (zpDesignCurrentLi && zpDesignCurrentDiv && zpDesignCurrentDiv.__zp_label) {
    // remove current href and get text
    var text = '', target = _el("f_design_target").value;
    for (var i = 0; i < zpDesignCurrentLi.childNodes.length; i++) {
      var child = zpDesignCurrentLi.childNodes[i];
      if (child.nodeType == 1) { // ELEMENT_NODE
        if (child.tagName.toLowerCase() == 'a') {
          target = child.getAttribute('target');
          for (var j = 0; j < child.childNodes.length; j++) {
            var c = child.childNodes[j];
            if (c.nodeType == 3 && c.data) {
              text = c.data;
              break;
            }
          }
          zpDesignCurrentLi.removeChild(child);
        }
      } else if (child.nodeType == 3) { // TEXT_NODE
        if (child.data) {
          text = child.data;
        }
        zpDesignCurrentLi.removeChild(child);
      }
    }
    while (zpDesignCurrentDiv.__zp_label.firstChild) {
      zpDesignCurrentDiv.__zp_label.removeChild(zpDesignCurrentDiv.__zp_label.firstChild);
    }
    // insert new href
    designChangeCurrentLi(text, href, target);
  }
};

function designChangeCurrentTarget(target) {
  if (zpDesignCurrentLi && zpDesignCurrentDiv && zpDesignCurrentDiv.__zp_label) {
    // remove current href and get text
    var text = '', href = _el("f_design_link").value;
    for (var i = 0; i < zpDesignCurrentLi.childNodes.length; i++) {
      var child = zpDesignCurrentLi.childNodes[i];
      if (child.nodeType == 1) { // ELEMENT_NODE
        if (child.tagName.toLowerCase() == 'a') {
          href = child.getAttribute('href');
          for (var j = 0; j < child.childNodes.length; j++) {
            var c = child.childNodes[j];
            if (c.nodeType == 3 && c.data) {
              text = c.data;
              break;
            }
          }
          zpDesignCurrentLi.removeChild(child);
        }
      } else if (child.nodeType == 3) { // TEXT_NODE
        if (child.data) {
          text = child.data;
        }
        zpDesignCurrentLi.removeChild(child);
      }
    }
    while (zpDesignCurrentDiv.__zp_label.firstChild) {
      zpDesignCurrentDiv.__zp_label.removeChild(zpDesignCurrentDiv.__zp_label.firstChild);
    }
    // insert new href
    designChangeCurrentLi(text, href, target);
  }
};

function designChangeCurrentLi(text, href, target) {
  if (href != null && href != '' && href != 'none') {
    _el("f_design_target").disabled = false;
    var a = document.createElement('a');
    a.setAttribute('href', href);
    if (target != null && target != '') {
      a.setAttribute('target', target);
    }
    a.appendChild(document.createTextNode(text));
    var nextNode = zpDesignCurrentLi.firstChild;
    if (nextNode && nextNode.tagName.toLowerCase() == 'img') {
      nextNode = nextNode.nextSibling;
    }
    zpDesignCurrentLi.insertBefore(a, nextNode);
    zpDesignCurrentDiv.__zp_label.appendChild(a.cloneNode(true));
  } else {
    _el("f_design_target").disabled = true;
    var nextNode = zpDesignCurrentLi.firstChild;
    if (nextNode && nextNode.tagName.toLowerCase() == 'img') {
      nextNode = nextNode.nextSibling;
    }
    zpDesignCurrentLi.insertBefore(document.createTextNode(text), nextNode);
    var span = document.createElement('span');
    span.appendChild(document.createTextNode(text));
    zpDesignCurrentDiv.__zp_label.appendChild(span);
  }
};

function designInitLi(li) {
  if (li.style.cursor) {
    li.style.cursor = "pointer";
  }
  li.onclick = function(ev, testElm) {
    if (!testElm) {
      ev || (ev = window.event);
      if (!ev) {
        return false;
      }
      testElm = ev.currentTarget || ev.srcElement;
    }

    if (zpDesignCurrentLi) {
      Zapatec.Utils.removeClass(zpDesignCurrentLi, "active");
    }
    zpDesignCurrentLi = li;
    Zapatec.Utils.addClass(zpDesignCurrentLi, "active");

    var input = _el("f_design_label");
    var input_link = _el("f_design_link");
    var input_target = _el("f_design_target");
    input.disabled = false;
    input_link.disabled = false;
    input_target.disabled = true;
    var text = '', href = 'none', target = '', icon = false;
    for (var i = 0; i < li.childNodes.length; i++) {
      var child = li.childNodes[i];
      if (child.nodeType == 1) { // ELEMENT_NODE
        var tag = child.tagName.toLowerCase();
        if (tag == 'img') {
          icon = true;
        } else if (tag == 'a') {
          href = child.getAttribute('href');
          target = child.getAttribute('target');
          for (var j = 0; j < child.childNodes.length; j++) {
            var c = child.childNodes[j];
            if (c.nodeType == 3 && c.data) {
              var t = c.data;
              t = t.replace(/^\s+/, '').replace(/\s+$/, '');
              if (t != '') {
                text = t;
                break;
              }
            }
          }
          if (text != '') {
            break;
          }
        }
      } else if (child.nodeType == 3 && child.data) { // TEXT_NODE
        var t = child.data;
        t = t.replace(/^\s+/, '').replace(/\s+$/, '');
        if (t != '') {
          text = t;
          break;
        }
      }
    }
    input.value = text;
    input_link.value = href;
    input_target.value = target;
    if (href != '' && href != 'none') {
      input_target.disabled = false;
    }

    if (_el("pane-contents").style.display != 'none') { // We're on Contents pane
      input.select();
      input.focus();
    }

    if (icon) {
      _el("designAddIcon").style.display = 'none';
      _el("designRemoveIcon").style.display = 'block';
    } else {
      _el("designRemoveIcon").style.display = 'none';
      _el("designAddIcon").style.display = 'block';
    }

    while (testElm && !testElm.__zp_item) {
      testElm = testElm.parentNode;
    }
    if (testElm) {
      ContentsMenu.setActiveItem(testElm);
    }
    customClasses.propertiesWind.hide();

    return false;
  };
};

function alertDesignNoSelectedNode() {
  alert('You need to click an item in the menu first');
};

function designInsert(after) {
  if (!zpDesignCurrentLi || !zpDesignCurrentDiv) {
    alertDesignNoSelectedNode();
    return false;
  }
  var newli = document.createElement('li');
  newli.innerHTML = 'New Item';
  customClasses.addClass(-1, newli, '', '', '', '', false);
  zpDesignCurrentLi.parentNode.insertBefore(newli, after ? zpDesignCurrentLi.nextSibling : zpDesignCurrentLi);
  designInitLi(newli);
  newli.onclick(null, newli);
  doContents();
  return false;
};

function designAddSubtree() {
  if (!zpDesignCurrentLi || !zpDesignCurrentDiv) {
    alertDesignNoSelectedNode();
    return false;
  }
  // assume the subtree already exists (it can happen)
  var newul = zpDesignCurrentLi.getElementsByTagName("ul")[0];
  if (!newul) {
    // only if not existent, create one
    newul = document.createElement("ul");
    customClasses.addClass(-1, newul, '', '', '', '', true);
    zpDesignCurrentLi.appendChild(newul);
  }
  var newli = document.createElement("li");
  customClasses.addClass(-1, newli, '', '', '', '', false);
  newul.appendChild(newli);
  newli.innerHTML = "New Item";
  designInitLi(newli);
  newli.onclick(null, newli);
  doContents();
  return false;
};

function designRemove() {
  if (!zpDesignCurrentLi || !zpDesignCurrentDiv) {
    alertDesignNoSelectedNode();
    return false;
  }
  var p = zpDesignCurrentLi.parentNode, p2 = p;
  if (p.id == 'designMenu' && p.childNodes.length == 1) {
    alert("Sorry, you can't remove all menu items because menu must have at least one item to be operational.");
  } else if (confirm("Remove selected item and any submenus it might have?")) {
    p.removeChild(zpDesignCurrentLi);
    if (!/\S/.test(p.innerHTML)) {
      p2 = p.parentNode;
      p2.removeChild(p);
      p = p2;
    }
    zpDesignCurrentLi = null;
    _el("f_design_label").disabled = true;
    _el("f_design_link").disabled = true;
    _el("f_design_target").disabled = true;
    doContents();
  }
  return false;
};

function designAddIcon() {
  if (!zpDesignCurrentLi || !zpDesignCurrentDiv) {
    alertDesignNoSelectedNode();
    return false;
  }
  iconWind.show(
    function(src) {
      if (src) {
        var icon = document.createElement('img');
        icon.setAttribute('src', src);
        // Store relative path, DOM will setAttribute to absolute path
        icon.setAttribute('zpPathRelative', 
          src.replace(/^.*\/themes\/icon\//, "zpmenu/themes/icon/"))
        zpDesignCurrentLi.insertBefore(icon, zpDesignCurrentLi.firstChild);
        _el("designAddIcon").style.display = 'none';
        _el("designRemoveIcon").style.display = 'block';
        doContents();
      }
    }
  );
  return false;
}

function designRemoveIcon() {
  if (!zpDesignCurrentLi || !zpDesignCurrentDiv) {
    alertDesignNoSelectedNode();
    return false;
  }
  for (var i = 0; i < zpDesignCurrentLi.childNodes.length; i++) {
    var child = zpDesignCurrentLi.childNodes[i];
    if (child.nodeType == 1 && child.tagName.toLowerCase() == 'img') {
      zpDesignCurrentLi.removeChild(child);
    }
  }
  _el("designRemoveIcon").style.display = 'none';
  _el("designAddIcon").style.display = 'block';
  doContents();
  return false;
}

/**
 * ContentsMenu class derived from Zapatec.Menu class. It modifies createItem method of the class.
 * Here we are using inheritence method described at http://www.kevlindev.com/tutorials/javascript/inheritance/
 */

ContentsMenu.prototype = new Zapatec.Menu();
ContentsMenu.SUPERclass = Zapatec.Menu.prototype;

function ContentsMenu(el) {
  if (arguments.length > 0) {
    this.init(el);
  }
};

ContentsMenu.prototype.init = function(el) {
  ContentsMenu.SUPERclass.init.call(this, {
    theme: strCurrentThemeName,
    source: el,
    vertical: getConfigParam("f_vertical", false),
    hideDelay: 86400000
  });
  if (zpDesignCurrentDiv) {
    this.sync(zpDesignCurrentDiv.__zp_item);
  }
};

ContentsMenu.prototype.createItem = function(li, parent, next_li, level, intItem) {
  var item = ContentsMenu.SUPERclass.createItem.call(this, li, parent, next_li, level, intItem);
  if (item) {
    var node = item;
    while (node = node.lastChild) {
      var tag = node.tagName.toLowerCase();
      if (tag == 'a' || tag == 'span') {
        item.__zp_label = node.parentNode;
        break;
      }
    }
    if (item.className.indexOf('active') >= 0) {
      zpDesignCurrentDiv = item;
      setTimeout(function(){ContentsMenu.setActiveItem(item)}, 1);
    }
    var cn = customClasses.getClassNumber(item);
    if (cn >= 0 && customClasses.classes[cn]) {
      item.onclick = customClasses.classes[cn].node.onclick;
    } else {
      item.onclick = function(e) {
        return false;
      };
    }
    var self = this;
    item.onmouseover = function() {
      self.itemMouseHandler(item.__zp_item, Zapatec.Menu.MOUSEOVER);
    };
  }
  return item;
};

ContentsMenu.prototype.sync = function(item_id) {
  if (!this.items) {
    return;
  }
  var item = this.items[item_id];
  if (!item) {
    return;
  }
  this.collapseAll();
  this.selectedItem = item;
  var a = [];
  while (item.__zp_parent) {
    a[a.length] = item;
    var pt = this._getTree(item.__zp_parent);
    if (pt.__zp_item)
      item = this.items[pt.__zp_item];
    else
      break;
  }
  for (var i = a.length; --i >= 0;) {
    a[i].onmouseover();
  }
  Zapatec.Utils.addClass(this.selectedItem, "zpMenu-item-selected");
};

ContentsMenu.prototype.refreshSubMenu = function() {
  if (!zpDesignCurrentDiv) return;
  var subMenu = zpDesignCurrentDiv.parentNode;
  var isTopMenu = (subMenu == this.top_parent.__zp_menu);
  var subMenuWidth = 0;
  var subMenuHeight = 0;
  var item = subMenu.firstChild;
  if (!item) return;
  var itemMarginLeft = item.offsetLeft;
  var itemMarginTop = item.offsetTop;
  while (item) {
    var itemWidth = item.offsetWidth + itemMarginLeft;
    var itemHeight = item.offsetHeight + itemMarginTop;
    if (!isTopMenu || this.config.vertical) {
      if (itemWidth > subMenuWidth) {
        subMenuWidth = itemWidth;
      }
      subMenuHeight += itemHeight;
    } else {
      subMenuWidth += itemWidth;
      if (itemHeight > subMenuHeight) {
        subMenuHeight = itemHeight;
      }
    }
    item = item.nextSibling;
  }
  if (subMenuWidth > 0 && subMenuHeight > 0) {
    if (!isTopMenu) {
      // + item right and bottom margin
      subMenuWidth += itemMarginLeft;
      subMenuHeight += itemMarginTop;
      if (typeof subMenu.clientLeft != 'undefined') { // IE & Opera
        // + submenu margin
        subMenuWidth += subMenu.offsetLeft * 2;
        subMenuHeight += subMenu.offsetTop * 2;
      }
      subMenu = subMenu.parentNode;
    }
    if (subMenu.clientLeft) { // IE & Opera
      // + submenu border
      subMenuWidth += subMenu.clientLeft * 2;
      subMenuHeight += subMenu.clientTop * 2;
    }
    if (isTopMenu || subMenu.clientWidth < subMenuWidth) {
      subMenu.style.width = subMenuWidth + 'px';
    }
    if (!isTopMenu) {
      subMenu.style.height = subMenuHeight + 'px';
    }
  }
};

ContentsMenu.setActiveItem = function(item) {
  if (zpDesignCurrentDiv && zpDesignCurrentDiv.__zp_border) {
    // Unselect current item
    zpDesignCurrentDiv.removeChild(zpDesignCurrentDiv.__zp_border);
    zpDesignCurrentDiv.__zp_border = null;
  }
  zpDesignCurrentDiv = item;
  var border = zpDesignCurrentDiv.__zp_border = Zapatec.Utils.createElement('div');
  border.style.position = 'absolute';
  border.style.border = '1px solid #f00';
  border.style.left = zpDesignCurrentDiv.offsetLeft + 'px';
  border.style.top = zpDesignCurrentDiv.offsetTop + 'px';
  var width = zpDesignCurrentDiv.clientWidth;
  if (zpDesignCurrentDiv.clientLeft) {
    // IE & Opera
    width += zpDesignCurrentDiv.clientLeft * 2; // + border
  }
  border.style.width = width + 'px';
  var height = zpDesignCurrentDiv.clientHeight;
  if (zpDesignCurrentDiv.clientTop) {
    // IE & Opera
    height += zpDesignCurrentDiv.clientTop * 2; // + border
  }
  border.style.height = height + 'px';
  zpDesignCurrentDiv.insertBefore(border, zpDesignCurrentDiv.firstChild);
};

function doContents() {
  customClasses.apply(); // Apply custom properties
  var ul = _el('designMenu');
  var div = _el('designcontents');
  var clone = ul.cloneNode(true);
  clone.id = 'tree-contents';
  while (div.firstChild) {
    div.removeChild(div.firstChild);
  }
  div.appendChild(clone);
  contentsMenu = new ContentsMenu('tree-contents');
  customClasses.apply(); // Apply custom properties
};

/**
 * Look and Feel pane
 */

function designItemProperties() {
  if (!zpDesignCurrentLi) {
    alertDesignNoSelectedNode();
  } else {
    customClasses.propertiesDialogLi(zpDesignCurrentLi);
  }
  return false;
};

function designSubmenuProperties() {
  if (!zpDesignCurrentLi) {
    alertDesignNoSelectedNode();
  } else {
    customClasses.propertiesDialogUl(zpDesignCurrentLi.parentNode);
  }
  return false;
};

function designCustomize() {
  customClasses.propertiesDialogMenu();
  return false;
};

function doLook() {
  customClasses.apply(); // Apply custom properties
  var ul = _el('designMenu');
  var div = _el('designlook');
  while (div.firstChild) {
    div.removeChild(div.firstChild);
  }
  var clone = ul.cloneNode(true);
  clone.id = 'tree-look';
  div.appendChild(clone);
  contentsMenu = new ContentsMenu('tree-look');
  customClasses.apply(); // Apply custom properties
};

/*
 * Effects pane. Show the menu with the options that the user
 * selected.
 */

function targetBlank(node) {
  for (var i=0; i<node.childNodes.length; i++) {
    var child = node.childNodes[i];
    if (child.nodeType == 1) { // ELEMENT_NODE
      var tag = child.tagName.toLowerCase();
      if (tag == 'a') {
        var href = child.getAttribute('href');
        if (href != null && href != '') {
          child.setAttribute('href', 'javascript:(function(){window.open("'+href+'","linkPreview")})()');
          child.removeAttribute('target');
        }
      } else if (tag == 'ul' || tag == 'li') {
        targetBlank(child);
      }
    }
  }
};

/*
 * Reference to preview menu object.
 */
var oPreviewMenu;

function doPreview() {
  customClasses.apply(); // Apply custom properties
  var config = getConfig();
  var ul = _el('designMenu');
  var div = _el('designpreview');
  while (div.firstChild) {
    div.removeChild(div.firstChild);
  }
  var clone = ul.cloneNode(true);
  clone.id = 'tree-preview';
  targetBlank(clone);
  div.appendChild(clone);
  config.theme = strCurrentThemeName;
  config.source = 'tree-preview';
  // Free memory
  if (oPreviewMenu) {
    oPreviewMenu.discard();
  }
  // Reinitialize menu
  oPreviewMenu = new Zapatec.Menu(config);
  if (getConfigParam('f_glide', false))
    oPreviewMenu.addAnimation('glide');
  if (getConfigParam('f_fade', false))
    oPreviewMenu.addAnimation('fade');
  if (getConfigParam('f_slide', false))
    oPreviewMenu.addAnimation('slide');
  if (getConfigParam('f_wipe', false))
    oPreviewMenu.addAnimation('wipe');
  if (getConfigParam('f_unfurl', false))
    oPreviewMenu.addAnimation('unfurl');
  customClasses.apply(); // Apply custom properties
};

/*
 * Get Your Code pane.
 */

function generateCode() {
  var indent1 = makeIndent(1);
  var indent2 = makeIndent(2);
  var indent3 = makeIndent(3);
  var indent4 = makeIndent(4);
  var html = getHeaders();
  html += indent1 + '<body>\n';
  html += indent2 + ('<!-- The HTML for the menu-->\n');
  html += getHTML(2, _el('designMenu'));
  html += indent2 + ('<!-- The Javascript code to initiate the menu -->\n');
  html += indent2 + '<script type="text/javascript">\n';
  html += indent3 + 'var myMenu = new Zapatec.Menu({\n';
  html += indent4 + 'theme: "' + _el('theme').value + '",\n';
  html += indent4 + 'source: "designMenu"';
  var strParms="";
  var config = getConfig();
  for (var prop in config) {
    var val = config[prop];
    // Skip default values
    if (typeof val == 'boolean' && !val) continue;
    else if (prop == 'showDelay' && val == 0) continue;
    else if (prop == 'hideDelay' && val == 500) continue;
    else if (prop == 'animSpeed' && val == 10) continue;
    if (typeof val == 'string') val = '"' + val + '"';
    strParms += (strParms ? ',' : '') + '\n' + indent4 + prop + ': ' + val;
  }
  if (getConfigParam('f_glide', false)) {
    strParms += (strParms ? ',' : '') + '\n' + indent4 + 'glide: true';
  }
  if (getConfigParam('f_fade', false)) {
    strParms += (strParms ? ',' : '') + '\n' + indent4 + 'fade: true';
  }
  if (getConfigParam('f_slide', false)) {
    strParms += (strParms ? ',' : '') + '\n' + indent4 + 'slide: true';
  }
  if (getConfigParam('f_wipe', false)) {
    strParms += (strParms ? ',' : '') + '\n' + indent4 + 'wipe: true';
  }
  if (getConfigParam('f_unfurl', false)) {
    strParms += (strParms ? ',' : '') + '\n' + indent4 + 'unfurl: true';
  }
  if (getConfigParam('f_trigger', false)) {
    var triggerEvent = getConfigParam('f_triggerevent', 1);
    if (triggerEvent == 4) {
      var triggerKey = getConfigParam('f_triggerkey', '');
      triggerKey = triggerKey.replace(/[^\d]/g, '');
      if (triggerKey != '') {
        strParms += (strParms ? ',' : '') + '\n' + indent4;
        strParms += 'triggerEvent: "keydown"';
        strParms += (strParms ? ',' : '') + '\n' + indent4;
        strParms += 'triggerKey: ' + triggerKey;
        var triggerObject = getConfigParam('f_triggerobject', '');
        if (triggerObject != '') {
          strParms += (strParms ? ',' : '') + '\n' + indent4;
          strParms += 'triggerObject: "' + triggerObject + '"';
        }
      }
    } else {
      strParms += (strParms ? ',' : '') + '\n' + indent4;
      strParms += 'triggerEvent: "mouseup"';
      if (triggerEvent == 2) {
        strParms += (strParms ? ',' : '') + '\n' + indent4;
        strParms += 'triggerKey: "left"';
      } else if (triggerEvent == 3) {
        strParms += (strParms ? ',' : '') + '\n' + indent4;
        strParms += 'triggerKey: "both"';
      }
      var triggerObject = getConfigParam('f_triggerobject', '');
      if (triggerObject != '') {
        strParms += (strParms ? ',' : '') + '\n' + indent4;
        strParms += 'triggerObject: "' + triggerObject + '"';
      }
    }
  }
  if (getConfigParam('f_keeptrack', false)) {
    strParms += (strParms ? ',' : '') + '\n' + indent4;
    if (getConfigParam('f_keeptrackexpand', false)) {
      strParms += 'rememberPath: "expand"';
    } else {
      strParms += 'rememberPath: true';
    }
    var pathCookie = getConfigParam('f_keeptrackcookie', '');
    if (pathCookie != '') {
      strParms += (strParms ? ',' : '') + '\n' + indent4;
      strParms += 'pathCookie: "' + pathCookie + '"';
    }
  }
  if (strParms != '') {
    html += ',' + strParms + '\n' + makeIndent(3);
  }
  html += '});\n';
  html += indent2 + '</script>\n';
  html += indent2 + '<noscript>\n'
  html += indent3 + '<br/>\n';
  html += indent3 + 'This page uses a <a href="http://www.zapatec.com/website/main/products/menu/"> Javascript Menu</a>,\n';
  html += indent3 + 'but your browser does not support Javascript.\n';
  html += indent3 + '<br/>\n';
  html += indent3 + 'Either enable Javascript in your Browser or upgrade to a newer version.\n';
  html += indent2 + '</noscript>\n';
  html += indent2 + '<br/>\n';
  html += indent2 + '<a href="http://www.zapatec.com/website/main/products/menu/">Zapatec Javascript Menu</a>\n';
  html += indent1 + '</body>\n</html>';
  return html;
};

var initialSubMenus = initialMenuItems = 0; //has the menu been created yet?

function paneContents() {
  customClasses.unApply(); // Remove custom properties

  // The UL underneath which everything else is set up
  var mainUl = _el("designMenu"); 
  var numSubMenus = _el("f_subMenus").value; 
  var itemsInSubMenus = _el("f_itemsInSubMenus").value; 
  var img;

  if (initialMenuItems != 0) {//if it's zero -- first round don't need to check anything.
    if ((initialSubMenus == numSubMenus) && (initialMenuItems == itemsInSubMenus)) {
      return; //no change don't need to create
    }

    //change, confirm that they want it
    if (!confirm("You changed the number of menus or submenus.\nYou will loose any work you have done. Are you sure?")) {
      return;
    } else {
      customClasses = new CustomClasses();
      customClasses.addClass(-1, mainUl, '', '', '', '', true);
      while (mainUl.hasChildNodes())
        mainUl.removeChild(mainUl.lastChild);
    }
  } else {
    customClasses.addClass(-1, mainUl, '', '', '', '', true);
  }

  for (ii = 0; ii < numSubMenus; ii++) {
    //Create a <li> under the main <UL>
    var newLi = document.createElement("li");
    newLi.innerHTML='Menu ' + (ii + 1);
    newLi.className = 'wizardUl';
    customClasses.addClass(-1, newLi, '', '', '', '', false);
    mainUl.appendChild(newLi);
    designInitLi(newLi);

    //Create a <ul> under the <li> we just created
    var newUl = document.createElement("ul");
    customClasses.addClass(-1, newUl, '', '', '', '', true);
    newLi.appendChild(newUl)
      for (jj = 0; jj < itemsInSubMenus; jj++) {
        var subLi = document.createElement("li");
        subLi.innerHTML='Item ' + (jj + 1);
        customClasses.addClass(-1, subLi, '', '', '', '', false);
        newUl.appendChild(subLi);
        designInitLi(subLi);
      }
  }

  initialSubMenus = numSubMenus;
  initialMenuItems = itemsInSubMenus;
};

function makeCode() {
  var ta = _el("f_code");
  ta.value = generateCode();
};

function getConfig() {
  var config = {};
  config.onClick = getConfigParam("f_onclick", false);
  config.vertical = getConfigParam("f_vertical", false);
  config.drag = getConfigParam("f_drag", false);
  config.scrollWithWindow = getConfigParam("f_scroll", false);
  if (getConfigParam("f_dropshadow", false))
    config.dropShadow = 25;
  else
    config.dropShadow = false;
  config.showDelay = getConfigParam("f_showdelay", 0);
  config.hideDelay = getConfigParam("f_hidedelay", 500);
  config.animSpeed = getConfigParam("f_animspeed", 10);
  return config;
};

/* functions that return the HTML code */

function makeIndent(indent) {
  var str = "";
  while (indent-- > 0)
    str += "    ";
  return str;
};

function htmlEncode(str) {
  str = str.replace(/&/ig, "&amp;");
  str = str.replace(/</ig, "&lt;");
  str = str.replace(/>/ig, "&gt;");
  str = str.replace(/\x22/ig, "&quot;");
  return str;
};

function checkOutputNode(node) {
  if (!node.tagName || node.tagName == '!' /* IE anomaly */)
    throw "Node has no tag";
  if (node.className) {
    if (/(^|\s)not-in-output(\s|$)/i.test(node.className))
      throw "class: not-in-output";
  }
};

function getHTML(indent, node, compact, noRoot) {
  var html = "";
  if (node.nodeType == 3) {
    var str = node.data;
    str = str.replace(/(^\s+|\s+$)/g, "");
    if (str) {
      if (!compact)
        html += makeIndent(indent);
      html += htmlEncode(str);
      if (!compact)
        html += "\n";
    }
    return html;
  } else try {
    checkOutputNode(node); // throws an exception if node shouldn't be displayed
    var tag = node.tagName.toLowerCase();
    if (!noRoot) {
      html += makeIndent(indent) + "<" + tag;
      var a = ["href", "target", "src", "rel", "type", "http-equiv", "content", "alt"];
      for (var i = 0; i < a.length; ++i) {
        if (!(tag == 'img' && a[i] == 'href')) { // IE bug
          var val = node.getAttribute(a[i]);
          if (tag=='img' && a[i]=='src')
            val=node.getAttribute('zpPathRelative') || val
          if (val)
            html += ' ' + a[i] + '="' + val + '"';
        }
      }
      if (node.id == "designMenu")
        html += ' id="' + node.id + '"';
      var enter = true;
      if (!node.firstChild &&
          /^(link|meta|img|br|hr)$/i.test(tag)) {
        html += " />\n";
        noRoot = true;
        enter = false;
      } else {
        if (tag == 'ul' || tag == 'li') {
          if (customClasses.customized(node)) {
            html += ' class="' + customClasses.getClassName(node) + '"';
          }
        }
        html += ">";
      }
      if (enter && !compact)
        html += "\n";
    }
    for (var i = node.firstChild; i; i = i.nextSibling)
      html += getHTML(indent + 1, i, compact);
    if (!noRoot) {
      if (!compact)
        html += makeIndent(indent);
      html += "</" + tag + ">";
      html += "\n";
    }
    return html;
  } catch(e) {
    return "";
  }
};

function getHeaders() {
  var headers = '<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/1999/REC-html401-19991224/loose.dtd">\n';
  headers += "<html>\n";
  headers += makeIndent(1) + "<head>\n";
  headers += makeIndent(2) + '<title>Javascript Menu By Zapatec</title>\n';
  headers += makeIndent(2) + ('<!-- Works if this file is in the zpmenu folder. Adjust for other locations.-->\n\n');

  headers += makeIndent(2) + ('<!-- Javascript modules required for the menu-->\n');
  headers += makeIndent(2) + '<script src="utils/zapatec.js" type="text/javascript"></script>\n';

  headers += makeIndent(2) + ('<!-- basic Javascript file for the menu-->\n');
  headers += makeIndent(2) + '<script src="zpmenu/src/zpmenu.js" type="text/javascript"></script>\n';

  var strCustomClasses = customClasses.getHeaders(makeIndent(2));
  if (strCustomClasses) {
    headers += strCustomClasses;
  }

  headers += makeIndent(1) + "</head>\n";
  return(headers);
}

/*
 * Set the active theme for the 
 */

function setActiveTheme(objSelectBox) {
  strCurrentThemeName = objSelectBox.value;
  doLook();
};

function getConfigParam(id, elDefault) { 
  var returnValue = elDefault;
  var element = _el(id);
  if (element) {
    if (element.tagName.toLowerCase() == 'select') {
      // selectbox
      returnValue = element.value * 1; // convert to number
    } else if (element.getAttribute('type').toLowerCase() == 'text') {
      // text
      var value = element.value;
      value = value.replace(/[^\w-]/g, '');
      if (value != '') {
        returnValue = value;
      }
    } else {
      // checkbox
      returnValue = element.checked;
    }
  } 
  return returnValue;
};

/*
 * CustomClass class 
 */

function CustomClass(cssStyle, cssFont, cssStyleHi, cssFontHi, isContainerClass, node) {
  this.cssStyle = cssStyle;                 // Properties applied to item div
  this.cssFont = cssFont;                   // Properties applied to item label
  this.cssStyleHi = cssStyleHi;             // Properties applied to highlighted item
  this.cssFontHi = cssFontHi;               // Properties applied to highlighted item label
  this.isContainerClass = isContainerClass; // This is container class
  this.node = node;                         // Item or container node
};

/*
 * CustomClasses class 
 */

function CustomClasses() {
  this.menuClass = new CustomClass('', '', '', ''); // Whole menu properties
  this.classes = new Array();
  this.styleSheet = new Zapatec.StyleSheet();
  this.propertiesWind = new PropertiesWind();
};

CustomClasses.prototype.addClass = function(i, node, cssStyle, cssFont, cssStyleHi, cssFontHi, isContainerClass) {
  if (i >= 0 && this.classes[i]) {
    this.classes[i].cssStyle = cssStyle;
    this.classes[i].cssFont = cssFont;
    this.classes[i].cssStyleHi = cssStyleHi;
    this.classes[i].cssFontHi = cssFontHi;
    this.apply();
  } else {
    this.classes.push(new CustomClass(cssStyle, cssFont, cssStyleHi, cssFontHi, isContainerClass, node));
    i = this.classes.length - 1;
    Zapatec.Utils.addClass(node, 'zpMenuCust'+i);
  }
};

CustomClasses.prototype.getClassName = function(node) {
  var c = node.className;
  if (c) {
    var p = c.indexOf('zpMenuCust');
    if (p >= 0) {
      c = c.substring(p);
      p = c.indexOf(' ');
      if (p > 0) c = c.substring(0, p);
      return c;
    }
  }
  return '';
};

CustomClasses.prototype.getClassNumber = function(node) {
  var cn = -1;
  var c = node.className;
  if (c) {
    var p = c.indexOf('zpMenuCust');
    if (p >= 0) {
      cn = c.substring(p+10);
      p = cn.indexOf(' ');
      if (p > 0) cn = cn.substring(0, p);
      cn *= 1;
    }
  }
  return cn;
};

CustomClasses.prototype.customized = function(node) {
  var cn = this.getClassNumber(node);
  if (cn >= 0 && this.classes[cn]) {
    var c = this.classes[cn];
    if (c.cssStyle || c.cssFont || c.cssStyleHi || c.cssFontHi) {
      return true;
    }
  }
  return false;
};

CustomClasses.prototype.propertiesDialog = function(node, title, isContainerClass) {
  var cssStyle = '';
  var cssFont = '';
  var cssStyleHi = '';
  var cssFontHi = '';
  var cn = this.getClassNumber(node);
  if (cn>=0 && this.classes[cn]) {
    cssStyle = this.classes[cn].cssStyle;
    cssFont = this.classes[cn].cssFont;
    cssStyleHi = this.classes[cn].cssStyleHi;
    cssFontHi = this.classes[cn].cssFontHi;
  }
  var self = this;
  this.propertiesWind.show(cssStyle, cssFont, cssStyleHi, cssFontHi, title, false,
    function(cssStyle, cssFont, cssStyleHi, cssFontHi) {
      self.addClass(cn, node, cssStyle, cssFont, cssStyleHi, cssFontHi, isContainerClass);
      doLook(); // Redraw menu
    }
  );
};

CustomClasses.prototype.propertiesDialogLi = function(node) {
  this.propertiesDialog(node, 'Menu Item Properties', false);
};

CustomClasses.prototype.propertiesDialogUl = function(node) {
  this.propertiesDialog(node, 'Sub-Menu Properties', true);
};

CustomClasses.prototype.propertiesDialogMenu = function() {
  var self = this;
  var mc = this.menuClass;
  this.propertiesWind.show(mc.cssStyle, mc.cssFont, mc.cssStyleHi, mc.cssFontHi, 'Menu Properties', true,
    function(cssStyle, cssFont, cssStyleHi, cssFontHi) {
      mc.cssStyle = cssStyle;
      mc.cssFont = cssFont;
      mc.cssStyleHi = cssStyleHi;
      mc.cssFontHi = cssFontHi;
      self.apply();
      doLook(); // Redraw menu
    }
  );
};

CustomClasses.prototype.apply = function() {
  var ss = this.styleSheet;
  ss.removeRules(); // Clean style sheet
  var mc = this.menuClass;
  // Check if menu is initialized
  if (!contentsMenu) {
    return;
  }
  // Get class name prefix
  var strClassName = '.' + contentsMenu.getClassName({prefix: 'zpMenu'}) +
   ' .zpMenuContainer';
  // Apply custom properties to whole menu
  if (mc.cssStyle) {
    ss.addRule(strClassName + ' .zpMenu-item', mc.cssStyle);
    ss.addRule(strClassName + ' .zpMenuContainer .zpMenu-item', mc.cssStyle);
  }
  if (mc.cssFont) {
    ss.addRule(strClassName + ' .zpMenu-item .zpMenu-label', mc.cssFont);
    ss.addRule(strClassName + ' .zpMenu-item a', mc.cssFont);
    ss.addRule(strClassName + ' .zpMenuContainer .zpMenu-item .zpMenu-label',
     mc.cssFont);
    ss.addRule(strClassName + ' .zpMenuContainer .zpMenu-item a', mc.cssFont);
  }
  if (mc.cssStyleHi) {
    ss.addRule(strClassName + ' .zpMenu-item-selected', mc.cssStyleHi);
    ss.addRule(strClassName + ' .zpMenuContainer .zpMenu-item-selected',
     mc.cssStyleHi);
  }
  if (mc.cssFontHi) {
    ss.addRule(strClassName + ' .zpMenu-item-selected .zpMenu-label',
     mc.cssFontHi);
    ss.addRule(strClassName + ' .zpMenu-item-selected a', mc.cssFontHi);
    ss.addRule(strClassName +
     ' .zpMenuContainer .zpMenu-item-selected .zpMenu-label', mc.cssFontHi);
    ss.addRule(strClassName + ' .zpMenuContainer .zpMenu-item-selected a',
     mc.cssFontHi);
  }

  // Apply custom properties to items
  for (var i=0; i<this.classes.length; i++) {
    var ci = this.classes[i];
    if (ci.cssStyle) {
      ss.addRule(strClassName + ' .zpMenuCust'+i, ci.cssStyle);
      ss.addRule(strClassName + ' .zpMenuContainer .zpMenuCust'+i, ci.cssStyle);
      if (ci.isContainerClass) {
        ss.addRule(strClassName + ' .zpMenuCust'+i+' .zpMenuContainer',
         ci.cssStyle);
        ss.addRule(strClassName + ' .zpMenuContainer .zpMenuCust' + i +
         ' .zpMenuContainer', ci.cssStyle);
      }
    }
    if (ci.cssFont) {
      ss.addRule(strClassName + ' .zpMenuCust' + i + ' .zpMenu-label',
       ci.cssFont);
      ss.addRule(strClassName + ' .zpMenuCust' + i + ' a', ci.cssFont);
      ss.addRule(strClassName + ' .zpMenuContainer .zpMenuCust' + i +
       ' .zpMenu-label', ci.cssFont);
      ss.addRule(strClassName + ' .zpMenuContainer .zpMenuCust' + i +
       ' a', ci.cssFont);
    }
  }
};

CustomClasses.prototype.unApply = function() {
  this.styleSheet.removeRules(); // Clean style sheet
};

CustomClasses.prototype.getHeaders = function(indent) {
  // Check if menu is initialized
  if (!contentsMenu) {
    return;
  }
  // Get class name prefix
  var strClassName = '.' + contentsMenu.getClassName({prefix: 'zpMenu'}) +
   ' .zpMenuContainer';
  // Form headers
  var headers = '';
  if (this.classes.length > 0 || this.menuClass.cssStyle || this.menuClass.cssFont) {
    var indentpp = indent + makeIndent(1);
    var mc = this.menuClass;
    if (mc.cssStyle) {
      headers += indentpp + strClassName + ' .zpMenu-item { '+mc.cssStyle+' }\n';
      headers += indentpp + strClassName + ' .zpMenuContainer .zpMenu-item { '+mc.cssStyle+' }\n';
    }
    if (mc.cssFont) {
      headers += indentpp + strClassName + ' .zpMenu-item .zpMenu-label { '+mc.cssFont+' }\n';
      headers += indentpp + strClassName + ' .zpMenu-item a { '+mc.cssFont+' }\n';
      headers += indentpp + strClassName + ' .zpMenuContainer .zpMenu-item .zpMenu-label { '+mc.cssFont+' }\n';
      headers += indentpp + strClassName + ' .zpMenuContainer .zpMenu-item a { '+mc.cssFont+' }\n';
    }
    if (mc.cssStyleHi) {
      headers += indentpp + strClassName + ' .zpMenu-item-selected { '+mc.cssStyleHi+' }\n';
      headers += indentpp + strClassName + ' .zpMenuContainer .zpMenu-item-selected { '+mc.cssStyleHi+' }\n';
    }
    if (mc.cssFontHi) {
      headers += indentpp + strClassName + ' .zpMenu-item-selected .zpMenu-label { '+mc.cssFontHi+' }\n';
      headers += indentpp + strClassName + ' .zpMenu-item-selected a { '+mc.cssFontHi+' }\n';
      headers += indentpp + strClassName + ' .zpMenuContainer .zpMenu-item-selected .zpMenu-label { '+mc.cssFontHi+' }\n';
      headers += indentpp + strClassName + ' .zpMenuContainer .zpMenu-item-selected a { '+mc.cssFontHi+' }\n';
    }
    for (var i=0; i<this.classes.length; i++) {
      var ci = this.classes[i];
      if (ci.cssStyle) {
        headers += indentpp + strClassName + ' .zpMenuCust'+i+' { '+ci.cssStyle+' }\n';
        headers += indentpp + strClassName + ' .zpMenuContainer .zpMenuCust'+i+' { '+ci.cssStyle+' }\n';
        if (ci.isContainerClass) {
          headers += indentpp + strClassName + ' .zpMenuCust'+i+' .zpMenuContainer { '+ci.cssStyle+' }\n';
          headers += indentpp + strClassName + ' .zpMenuContainer .zpMenuCust'+i+' .zpMenuContainer { '+ci.cssStyle+' }\n';
        }
      }
      if (ci.cssFont) {
        headers += indentpp + strClassName + ' .zpMenuCust'+i+' .zpMenu-label { '+ci.cssFont+' }\n';
        headers += indentpp + strClassName + ' .zpMenuCust'+i+' a { '+ci.cssFont+' }\n';
        headers += indentpp + strClassName + ' .zpMenuContainer .zpMenuCust'+i+' .zpMenu-label { '+ci.cssFont+' }\n';
        headers += indentpp + strClassName + ' .zpMenuContainer .zpMenuCust'+i+' a { '+ci.cssFont+' }\n';
      }
    }
  }
  if (headers) {
    headers = indent + '<style type="text/css">\n' + headers + indent + '</style>\n';
  }
  return headers;
};

/*
 * Wind class
 */

function Wind(id, title) {
  this.div = null;
  this.titleDiv = null;
  if (arguments.length > 0) {
    this.init(id, title);
  }
};

Wind.prototype.init = function(id, title) {
  this.div = _el(id);
  var st = this.div.style;
  st.position = 'absolute';
  st.zIndex = 10;
  this.titleDiv = _el(id + 'Title');
  if (this.titleDiv) {
    for (var i = 0; i < this.titleDiv.childNodes.length; i++) {
      this.titleDiv.removeChild(this.titleDiv.childNodes[i]);
    }
    this.titleDiv.appendChild(document.createTextNode(title));
  }
  this.dragging = false;
  this.xOffs = 0;
  this.yOffs = 0;
  var self = this;
  Zapatec.Utils.addEvent(window.document, "mousedown",
    function(ev) { return self.dragStart(ev, self) });
  Zapatec.Utils.addEvent(window.document, "mousemove",
    function(ev) { return self.dragMove(ev, self) });
  Zapatec.Utils.addEvent(window.document, "mouseup",
    function(ev) { return self.dragEnd(ev, self) });
  // WCH for IE only
  this.wch = Zapatec.Utils.createWCH(this.div);
  if (this.wch) {
    this.wch.style.zIndex = -1;
  }
};

Wind.prototype.show = function() {
  if (this.div) {
    this.div.style.display = 'block';
    // WCH for IE only
    if (this.wch && typeof this.div.clientLeft != 'undefined') {
      var iBorderLeft = this.div.clientLeft;
      var iBorderTop = this.div.clientTop;
      Zapatec.Utils.setupWCH(this.wch, -iBorderLeft, -iBorderTop,
       this.div.offsetWidth + iBorderLeft * 2,
       this.div.offsetHeight + iBorderTop * 2);
    }
  }
};

Wind.prototype.hide = function() {
  if (this.div) {
    this.div.style.display = 'none';
  }
};

Wind.prototype.dragStart = function(ev, self) {
  ev || (ev = window.event);
  if (self.dragging) {
    return true;
  }
  var div = self.titleDiv || self.div;
  if (!div) {
    return true;
  }
  var testElm = ev.srcElement || ev.target;
  while (1) {
    if (testElm == div) break;
    else testElm = testElm.parentNode;
    if (!testElm) return true;
  }
  self.dragging = true;
  var posX = ev.pageX || ev.clientX + window.document.body.scrollLeft || 0;
  var posY = ev.pageY || ev.clientY + window.document.body.scrollTop || 0;
  var L = parseInt(this.div.style.left) || 0;
  var T = parseInt(this.div.style.top) || 0;
  self.xOffs = (posX - L);
  self.yOffs = (posY - T);
};

Wind.prototype.dragMove = function(ev, self) {
  ev || (ev = window.event);
  if (!(self && self.dragging)) {
    return false;
  }
  var posX = ev.pageX || ev.clientX + window.document.body.scrollLeft || 0;
  var posY = ev.pageY || ev.clientY + window.document.body.scrollTop || 0;
  var st = this.div.style, L = posX - self.xOffs, T = posY - self.yOffs;
  st.left = L + "px";
  st.top = T + "px";
  return Zapatec.Utils.stopEvent(ev);
};

Wind.prototype.dragEnd = function(ev, self) {
  if (!self) {
    return false;
  }
  self.dragging = false;
};

/*
 * PropertiesWind class
 */

function unpx(s) {
  var p = s.indexOf('px');
  if (p > 0) s = s.substring(0, p);
  if (isNaN(s)) return '';
  return s*1;
};

function unurl(s) {
  var p = s.indexOf(')');
  if (p > 0) s = s.substring(4, p);
  return s;
};

function first(s) {
  var p = s.indexOf(' ');
  if (p > 0) s = s.substring(0, p);
  return s;
};

function firstRgb(s) {
  var p = s.indexOf(' rgb(');
  if (p > 0) s = s.substring(0, p);
  return s;
};

function initColorChooser(field, id, val) {
  val = firstRgb(val);
  field.value = val;
  _el(id+'Chooser').style.backgroundColor = val;
};

function initSelectBox(field, val) {
  val = first(val);
  for (var i = 0; i < field.options.length; i++) {
    if (field.options[i].value == val) {
      field.selectedIndex = i;
      return;
    }
  }
};

function initCheckBox(field, val) {
  if (val) {
    field.checked = true;
  } else {
    field.checked = false;
  }
};

function initNumberInput(field, val) {
  val = unpx(val);
  if (val > 0) {
    field.value = val;
  } else {
    field.value = '';
  }
};

function initImageInput(field, val) {
  val = unurl(val);
  if (val != '') {
    field.value = val;
  } else {
    field.value = 'none';
  }
};

PropertiesWind.prototype = new Wind();
PropertiesWind.SUPERclass = Wind.prototype;

function PropertiesWind(cssStyle, cssFont, cssStyleHi, cssFontHi, title, showHighlighted, callback) {
  if (arguments.length > 0) {
    this.init(cssStyle, cssFont, cssStyleHi, cssFontHi, title, showHighlighted, callback);
  }
};

PropertiesWind.prototype.init = function(cssStyle, cssFont, cssStyleHi, cssFontHi, title, showHighlighted, callback) {
  PropertiesWind.SUPERclass.init.call(this, 'propertiesBox', title);
  this.callback = callback;
  var hi = _el('propertiesHighlighted').style;
  if (showHighlighted) {
    hi.display = 'block';
  } else {
    hi.display = 'none';
  }
  this.setup(cssStyle, cssFont, cssStyleHi, cssFontHi);
  var self = this;
  _el('propertiesButtonApply').onclick = function() {
    self.apply();
    return false;
  };
  _el('propertiesButtonReset').onclick = function() {
    self.setup('', '', '', '');
    self.callback('', '', '', '');
    self.callback('', '', '', '');
    self.callback('', '', '', ''); // For unknown reason this doesn't work from the first
    return false;
  };
  _el('propertiesButtonOk').onclick = function() {
    self.apply();
    self.hide();
    return false;
  };
  _el('propertiesButtonCancel').onclick = function() {
    self.hide();
    return false;
  };
};

PropertiesWind.prototype.setup = function(cssStyle, cssFont, cssStyleHi, cssFontHi) {
  var st = _el('propertiesExample').style;
  var fo = _el('propertiesExampleLabel').style;
  var sth = _el('propertiesExampleHi').style;
  var foh = _el('propertiesExampleLabelHi').style;

  st.cssText = cssStyle;
  fo.cssText = cssFont;
  sth.cssText = cssStyleHi;
  foh.cssText = cssFontHi;

  var f = document.propertiesForm;

  initNumberInput(f.p_width, st.width);
  initNumberInput(f.p_height, st.height);

  initColorChooser(f.p_backgroundColor, 'p_backgroundColor', st.backgroundColor);
  initImageInput(f.p_backgroundImage, st.backgroundImage);
  initColorChooser(f.p_color, 'p_color', fo.color);

  initColorChooser(f.p_backgroundColorHi, 'p_backgroundColorHi', sth.backgroundColor);
  initImageInput(f.p_backgroundImageHi, sth.backgroundImage);
  initColorChooser(f.p_colorHi, 'p_colorHi', foh.color);

  initNumberInput(f.p_borderWidth, st.borderWidth);
  initColorChooser(f.p_borderColor, 'p_borderColor', st.borderColor);
  initSelectBox(f.p_borderStyle, st.borderStyle);

  initSelectBox(f.p_fontFamily, fo.fontFamily);
  initCheckBox(f.p_fontBold, fo.fontWeight.indexOf('bold') >= 0);
  initCheckBox(f.p_fontItalic, fo.fontStyle.indexOf('italic') >= 0);
  initNumberInput(f.p_fontSize, fo.fontSize);

  initSelectBox(f.p_textAlign,fo.textAlign);
  initSelectBox(f.p_verticalAlign,fo.verticalAlign);
};

PropertiesWind.prototype.show = function(cssStyle, cssFont, cssStyleHi, cssFontHi, title, showHighlighted, callback) {
  if (arguments.length > 0) {
    this.init(cssStyle, cssFont, cssStyleHi, cssFontHi, title, showHighlighted, callback);
  }
  PropertiesWind.SUPERclass.show.call(this);
};

PropertiesWind.prototype.apply = function() {
  var st='';
  var fo='';
  var sth='';
  var foh='';
  var f = document.propertiesForm;

  if (f.p_width.value > 0) {
    st += 'width:' + f.p_width.value + 'px;';
  }
  if (f.p_height.value > 0) {
    st += 'height:' + f.p_height.value + 'px;';
  }

  if (f.p_borderColor.value) {
    st += 'border-color:' + f.p_borderColor.value + ';';
  }
  if (f.p_borderStyle.value) {
    st += 'border-style:' + f.p_borderStyle.value + ';';
  }
  if (f.p_borderWidth.value > 0) {
    st += 'border-width:' + f.p_borderWidth.value + 'px;';
  }

  if (f.p_backgroundColor.value) {
    st += 'background-color:' + f.p_backgroundColor.value + ';';
  }
  if (f.p_backgroundImage.value && f.p_backgroundImage.value != 'none') {
    st += 'background-image:url("' + f.p_backgroundImage.value + '");';
  }
  if (f.p_color.value) {
    fo += 'color:' + f.p_color.value + ';';
  }

  if (f.p_backgroundColorHi.value) {
    sth += 'background-color:' + f.p_backgroundColorHi.value + ';';
  }
  if (f.p_backgroundImageHi.value && f.p_backgroundImageHi.value != 'none') {
    sth += 'background-image:url("' + f.p_backgroundImageHi.value + '");';
  }
  if (f.p_colorHi.value) {
    foh += 'color:' + f.p_colorHi.value + ';';
  }

  if (f.p_fontFamily.value) {
    fo += 'font-family:' + f.p_fontFamily.value + ';';
  }
  if (f.p_fontBold.checked) {
    fo += 'font-weight:bold;';
  }
  if (f.p_fontItalic.checked) {
    fo += 'font-style:italic;';
  }
  if (f.p_fontSize.value > 0) {
    fo += 'font-size:' + f.p_fontSize.value + 'px;';
  }

  if (f.p_textAlign.value) {
    fo += 'text-align:' + f.p_textAlign.value + ';';
  }
  if (f.p_verticalAlign.value) {
    fo += 'vertical-align:' + f.p_verticalAlign.value + ';';
  }

  this.callback(st, fo, sth, foh);
};

/*
 * Color Chooser functions
 */

function chooseColor(id) {
  var f = _el(id);
  var span = _el(id+'Chooser');
  Dialog('../wizard/popups/select_color.html', 'colorDialog', 240, 182, function(color) {
    if (color) {
      span.style.backgroundColor = "#" + color;
      f.value = "#" + color;
      if (id == 'p_color') {
        _el('propertiesExampleLabel').style.color="#" + color;
      } else if (id == 'p_backgroundColor') {
        _el('propertiesExample').style.backgroundColor="#" + color;
      } else if (id == 'p_borderColor') {
        _el('propertiesExample').style.borderColor="#" + color;
      }
    }
  }, f.value);
};

function unsetColor(id) {
  var field=_el(id);
  var span=_el(id+'Chooser');
  field.value = "";
  span.style.backgroundColor = "";
  if (id == 'p_color') {
    _el('propertiesExampleLabel').style.color='';
  } else if (id == 'p_backgroundColor') {
    _el('propertiesExample').style.backgroundColor='';
  } else if (id == 'p_borderColor') {
    _el('propertiesExample').style.borderColor='';
  }
};

/*
 * IconTree class
 */

IconTree.prototype = new Zapatec.BasicTree();
IconTree.SUPERclass = Zapatec.BasicTree.prototype;

function IconTree(el, config) {
  if (arguments.length > 0) {
    this.init(el, config);
  }
  this.src = null;
};

IconTree.prototype.init = function(el, config) {
  IconTree.SUPERclass.init.call(this, el, config);
};

IconTree.prototype.onItemSelect = function() {
  var item = this.selectedItem;
  if (!/tree-item-more/i.test(item.className)) {
    var node = item;
    while (node.childNodes.length > 0) {
      node = node.childNodes[0];
      if (node.nodeName.toLowerCase() == 'img') {
        this.src = node.src;
        document.iconForm.i_url.value = '';
        break;
      }
    }
  } else {
    this.src = null;
  }
};

/*
 * IconWind class
 */

IconWind.prototype = new Wind();
IconWind.SUPERclass = Wind.prototype;

function IconWind(callback) {
  this.iconTree = null;
  var el = _el('iconTree');
  if (el) {
    var config = {
      hiliteSelectedNode : true,
      compact            : false,
      dynamic            : false,
      initLevel          : false,
      defaultIcons       : null
    };
    this.iconTree = new IconTree(el, config);
  }
  if (arguments.length > 0) {
    this.init(callback);
  }
};

IconWind.prototype.init = function(callback) {
  IconWind.SUPERclass.init.call(this, 'iconBox', 'Choose an Icon');
  this.callback = callback;
  var self = this;
  document.iconForm.onsubmit = function() {
    var url = document.iconForm.i_url.value || self.iconTree.src;
    if (url) {
      self.apply(url);
      self.hide();
    } else {
      alert('Please choose an icon or enter icon URL. To close Icon Gallery press Cancel.');
    }
    return false;
  };
  _el('iconButtonCancel').onclick = function() {
    self.hide();
    return false;
  };
};

IconWind.prototype.show = function(callback) {
  if (arguments.length > 0) {
    this.init(callback);
  }
  document.iconForm.i_url.value = '';
  IconWind.SUPERclass.show.call(this);
};

IconWind.prototype.apply = function(url) {
  this.callback(url);
};
