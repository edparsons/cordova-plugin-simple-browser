//
//  SpeedsharePlugin.h
//
//  Copyright (c) 2015 Osix Corp. All rights reserved.
//  Please see the LICENSE included with this distribution for details.
//

#import <Cordova/CDVPlugin.h>
#import <WebKit/WebKit.h>

@interface CDVSimpleBrowser : CDVPlugin <WKNavigationDelegate, WKScriptMessageHandler>


- (void)start:(CDVInvokedUrlCommand*)command;
- (void)stop:(CDVInvokedUrlCommand*)command;
- (void)bringToFront:(CDVInvokedUrlCommand*)command;
- (void)sendToBack:(CDVInvokedUrlCommand*)command;
- (void)back:(CDVInvokedUrlCommand*)command;
- (void)forward:(CDVInvokedUrlCommand*)command;

@end
