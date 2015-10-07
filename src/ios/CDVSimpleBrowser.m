
//
//  SpeedsharePlugin.m
//
//  Copyright (c) 2015 Osix Corp. All rights reserved.
//  Please see the LICENSE included with this distribution for details.
//

#import "CDVSimpleBrowser.h"

#define TOOLBAR_HEIGHT 44.0

@implementation CDVSimpleBrowser{
    UIView *containerView;
    WKWebView *htmlView;
    UIToolbar *toolbar;
    UIBarButtonItem *forwardButton;
    UIBarButtonItem *backButton;
}

#pragma mark -
#pragma mark Cordova Methods
-(void) pluginInitialize{
    WKWebViewConfiguration *theConfiguration = [[WKWebViewConfiguration alloc] init];
    [theConfiguration.userContentController addScriptMessageHandler:self name:@"speedshare"];
    theConfiguration.allowsInlineMediaPlayback = true;

    CGRect frame = CGRectMake(0, self.webView.superview.frame.size.height, self.webView.superview.frame.size.width, self.webView.superview.frame.size.height-20);
    containerView = [[UIView alloc] initWithFrame:frame];
    containerView.clipsToBounds = YES;
    containerView.hidden = YES;

    frame = CGRectMake(0, 0, self.webView.superview.frame.size.width, self.webView.superview.frame.size.height-20-TOOLBAR_HEIGHT);
    htmlView = [[WKWebView alloc] initWithFrame:frame configuration:theConfiguration];
    
    htmlView.navigationDelegate = self;
    
    containerView.layer.zPosition = 5;

    float toolbarY = self.webView.superview.bounds.size.height - 20  - TOOLBAR_HEIGHT;
    CGRect toolbarFrame = CGRectMake(0.0, toolbarY, self.webView.superview.bounds.size.width, TOOLBAR_HEIGHT);

    toolbar = [[UIToolbar alloc] initWithFrame:toolbarFrame];
    toolbar.alpha = 1.000;
    toolbar.autoresizesSubviews = YES;
    toolbar.autoresizingMask = (UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleTopMargin);
    toolbar.barStyle = UIBarStyleBlackOpaque;
    toolbar.clearsContextBeforeDrawing = NO;
    toolbar.clipsToBounds = NO;
    toolbar.contentMode = UIViewContentModeScaleToFill;
    toolbar.hidden = NO;
    toolbar.multipleTouchEnabled = NO;
    toolbar.opaque = NO;
    toolbar.userInteractionEnabled = YES;

    NSString* frontArrowString = NSLocalizedString(@"►", nil); // create arrow from Unicode char
    forwardButton = [[UIBarButtonItem alloc] initWithTitle:frontArrowString style:UIBarButtonItemStylePlain target:self action:@selector(goForward)];
    forwardButton.enabled = YES;
    forwardButton.imageInsets = UIEdgeInsetsZero;

    NSString* backArrowString = NSLocalizedString(@"◄", nil); // create arrow from Unicode char
    backButton = [[UIBarButtonItem alloc] initWithTitle:backArrowString style:UIBarButtonItemStylePlain target:self action:@selector(goBack)];
    backButton.enabled = YES;
    backButton.imageInsets = UIEdgeInsetsZero;
/*
    shareButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemDone target:self action:@selector(close)];
    shareButton.enabled = YES;
*/
    UIBarButtonItem* flexibleSpaceButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFlexibleSpace target:nil action:nil];

    UIBarButtonItem* fixedSpaceButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemFixedSpace target:nil action:nil];
    fixedSpaceButton.width = 20;

    [toolbar setItems:@[flexibleSpaceButton, backButton, fixedSpaceButton, forwardButton]];

    [containerView addSubview:toolbar];
    [containerView addSubview:htmlView];
    [self.webView.superview insertSubview:containerView aboveSubview:self.webView];
}

- (void)webView:(WKWebView *)webView didFinishNavigation:(WKNavigation *)navigation {
}

- (void)userContentController:(WKUserContentController *)userContentController
                            didReceiveScriptMessage:(WKScriptMessage *)message{
}

- (void)goBack {
    [htmlView goBack];
}

- (void)goForward {
    [htmlView goForward];
}

#pragma mark -
#pragma mark Cordova JS - iOS bindings
#pragma mark Methods
/*** Methods
 ****/

- (void)start:(CDVInvokedUrlCommand*)command {
    NSString* url = [command.arguments objectAtIndex:0];
    
    NSURLRequest *request = [NSURLRequest requestWithURL:[NSURL URLWithString:url] cachePolicy:NSURLRequestUseProtocolCachePolicy timeoutInterval:60.0];

    [htmlView loadRequest:request];
    containerView.hidden = NO;

    [UIView animateWithDuration:0.5f animations:^{
        containerView.frame = CGRectMake(0, 20, self.webView.superview.frame.size.width, self.webView.superview.frame.size.height-20);
    } completion:^ (BOOL finished) {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];
}
- (void)stop:(CDVInvokedUrlCommand*)command {
    [UIView animateWithDuration:0.5f animations:^{
        containerView.frame = CGRectMake(0, self.webView.superview.frame.size.height, self.webView.superview.frame.size.width, self.webView.superview.frame.size.height-20-44);
    } completion:^ (BOOL finished) {
        if (finished) {
            containerView.hidden = YES;
        }
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
    }];

}

- (void)back:(CDVInvokedUrlCommand*)command  {
    [htmlView goBack];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}

- (void)forward:(CDVInvokedUrlCommand*)command  {
    [htmlView goForward];
    CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void)bringToFront:(CDVInvokedUrlCommand*)command {
    [self.webView.superview bringSubviewToFront:containerView];
}

- (void)sendToBack:(CDVInvokedUrlCommand*)command {
    [self.webView.superview sendSubviewToBack:containerView];
}

@end

