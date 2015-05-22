/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.tinkerpop.gremlin.structure.io.object;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * Created by bryn on 17/05/15.
 */
public class LambdaObjectInputStream extends ObjectInputStream {



    private ByteClassLoader classLoader;

    public LambdaObjectInputStream(InputStream in) throws IOException, ClassNotFoundException {
        super(in);
        classLoader = new ByteClassLoader(LambdaObjectInputStream.class.getClassLoader());
        enableResolveObject(true);
    }

    @Override
    protected Object resolveObject(Object obj) throws IOException {
        try {
            if (obj instanceof Lambda) {
                Lambda lambda = (Lambda) obj;
                classLoader.defineExternalClass(lambda.getTargetClassName(), lambda.getTargetClassBytes());
                return ((Lambda) obj).getLambda(classLoader);

            }
            return obj;
        } catch (Throwable e) {
            throw new IOException(e);
        }
    }
}
