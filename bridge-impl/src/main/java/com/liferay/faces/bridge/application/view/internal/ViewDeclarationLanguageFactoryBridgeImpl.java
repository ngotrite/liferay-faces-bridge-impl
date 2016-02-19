/**
 * Copyright (c) 2000-2016 Liferay, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liferay.faces.bridge.application.view.internal;

import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageFactory;

import com.liferay.faces.util.application.view.ViewDeclarationLanguageWrapper;


/**
 * @author  Neil Griffin
 */
public class ViewDeclarationLanguageFactoryBridgeImpl extends ViewDeclarationLanguageFactory {

	// Private Data Members
	private ViewDeclarationLanguageFactory wrappedViewDeclarationLanguageFactory;

	public ViewDeclarationLanguageFactoryBridgeImpl(ViewDeclarationLanguageFactory viewDeclarationLanguageFactory) {
		this.wrappedViewDeclarationLanguageFactory = viewDeclarationLanguageFactory;
	}

	@Override
	public ViewDeclarationLanguage getViewDeclarationLanguage(String viewId) {

		ViewDeclarationLanguage wrappedViewDeclarationLanguage = getWrapped().getViewDeclarationLanguage(viewId);
		ViewDeclarationLanguage facesRuntimeViewDeclarationLanguage = wrappedViewDeclarationLanguage;

		while (facesRuntimeViewDeclarationLanguage instanceof ViewDeclarationLanguageWrapper) {
			ViewDeclarationLanguageWrapper viewDeclarationLanguageWrapper = (ViewDeclarationLanguageWrapper)
				facesRuntimeViewDeclarationLanguage;
			facesRuntimeViewDeclarationLanguage = viewDeclarationLanguageWrapper.getWrapped();
		}

		boolean facelets = facesRuntimeViewDeclarationLanguage.getClass().toString().toLowerCase().contains("facelet");

		if (facelets) {
			return wrappedViewDeclarationLanguage;
		}
		else {
			return new ViewDeclarationLanguageBridgeJspImpl(wrappedViewDeclarationLanguage);
		}
	}

	@Override
	public ViewDeclarationLanguageFactory getWrapped() {
		return wrappedViewDeclarationLanguageFactory;
	}
}