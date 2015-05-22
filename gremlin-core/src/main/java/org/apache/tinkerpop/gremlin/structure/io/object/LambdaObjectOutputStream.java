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
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bryn on 16/05/15.
 */
public class LambdaObjectOutputStream extends ObjectOutputStream {

    private Map<String, byte[]> serializedClasses = new HashMap<>();

    public LambdaObjectOutputStream(OutputStream out) throws IOException {
        super(out);
        enableReplaceObject(true);
    }

    @Override
    protected Object replaceObject(Object obj) throws IOException {
        if (obj instanceof SerializedLambda) {
            return getLambda((SerializedLambda) obj);
        }
        return obj;
    }

    private Lambda getLambda(SerializedLambda obj) {
        try {
            String implClass = obj.getImplClass();
            byte[] classBytes = serializedClasses.get(implClass);
            if (classBytes == null) {
                String resource = "/" + implClass.replace(".", "/") + ".class";
                Path path = Paths.get(LambdaObjectOutputStream.class.getResource(resource).toURI());
                classBytes = Files.readAllBytes(path);
                serializedClasses.put(implClass, classBytes);
            }

            return new Lambda(implClass, classBytes, obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

}
