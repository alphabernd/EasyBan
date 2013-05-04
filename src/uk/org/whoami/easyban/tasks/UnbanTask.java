/*
 * Copyright 2011 Sebastian Köhler <sebkoehler@whoami.org.uk>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.org.whoami.easyban.tasks;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import uk.org.whoami.easyban.datasource.DataSource;
import uk.org.whoami.easyban.util.ConsoleLogger;

public class UnbanTask implements Runnable {

    private DataSource data;

    public UnbanTask(DataSource data) {
        this.data = data;
    }

    @Override
    public void run() {
        Calendar cal = Calendar.getInstance();
        HashMap<String,Long> tmpBans = data.getTempBans();
        Iterator<String> it = tmpBans.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            if (tmpBans.get(name) != 100000 && (cal.getTimeInMillis() > tmpBans.get(name))) {
                data.unbanNick(name);
                ConsoleLogger.info("Temporary ban for "+ name +" has been removed");
            }
        }
    }

}
