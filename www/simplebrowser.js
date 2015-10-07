window.simpleBrowser = {
  browserStart: false,

  start: function(url) {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'start', [url]);
  },
  stop: function() {
    Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'stop', []);
  },
  hideView: function() {
    if (SSHtmlViewer.browserStart) {
      Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'hideView', []);
    }
  },
  showView: function() {
    if (SSHtmlViewer.browserStart) {
      Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'showView', []);
    }
  },
  forward: function() {
    if (SSHtmlViewer.browserStart) {
      Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'forward', []);
    }
  },
  back: function() {
    if (SSHtmlViewer.browserStart) {
      Cordova.exec(SSHtmlViewer.SSHTMLSuccess, SSHtmlViewer.SSHTMLError, 'SimpleBrowser', 'back', []);
    }
  }
};