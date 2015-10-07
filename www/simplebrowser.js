window.simpleBrowser = {
  browserStart: false,

  start: function(url) {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'start', [url]);
  },
  stop: function() {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'stop', []);
  },
  hideView: function() {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'hideView', []);
  },
  showView: function() {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'showView', []);
  },
  forward: function() {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'forward', []);
  },
  back: function() {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'back', []);
  }
};