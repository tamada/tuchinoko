package com.github.tuchinoko;

import java.io.IOException;
import java.net.URL;

import jp.sourceforge.talisman.xmlcli.CommandLinePlus;
import jp.sourceforge.talisman.xmlcli.OptionsBuilder;
import jp.sourceforge.talisman.xmlcli.XmlCliConfigurationException;
import jp.sourceforge.talisman.xmlcli.builder.OptionsBuilderFactory;

import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.w3c.dom.DOMException;

import com.github.tuchinoko.spi.ProcessorService;
import com.github.tuchinoko.utils.Author;
import com.github.tuchinoko.utils.Organization;
import com.github.tuchinoko.utils.Provider;

/**
 * Tuchinokoを実行するためのクラスです．
 * 
 * @author Haruaki Tamada
 */
public class Main{
    public Main(String[] args){
        try{
            Options options = buildOptions();
            CommandLineParser parser = new PosixParser();
            CommandLinePlus commandLine = new CommandLinePlus(parser.parse(options, args, false));
            Environment env = new Environment();
            System.out.println("help option: " + commandLine.hasOption("help"));
            System.out.println("processors: " + commandLine.getOptionValue("processors"));
            System.out.println("args length: " + commandLine.getArgs().length);
            if(commandLine.hasOption("help")
                    || commandLine.getOptionValue("processors") == null
                    || commandLine.getArgs().length == 0){
                showHelp(env, commandLine, options);
                return;
            }
            Context context = new Context(env);
            updateContext(context, commandLine);

            Tuchinoko tuchinoko = new Tuchinoko(context);
            tuchinoko.execute();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void updateContext(Context context, CommandLinePlus cl){
        for(String arg: cl.getArgs()){
            context.addTarget(arg);
        }
        String processorNames = cl.getOptionValue("processors");
        for(String processorName: processorNames.split(",")){
            context.addProcessorName(processorName);
        }

        context.setDestination(cl.getOptionValue("destination"));
    }

    private void showHelp(Environment env, CommandLinePlus commandLine, Options options){
        String processorName = commandLine.getOptionValue("processors");
        if(processorName == null){
            showSimpleHelp(commandLine, env, options);
        }
        else{
            boolean flag = false;
            for(String pName: processorName.split(",[ \t]*")){
                flag = flag | showProcessorHelp(pName, env, options);
            }
            if(flag){
                showProcessorList(env);
            }
        }
    }

    private void showUnknownProcessor(String processorName){
        System.out.printf("%s: unknown%n", processorName);
    }

    private boolean showProcessorHelp(String processorName, Environment env, Options options){
        boolean flag = false;
        ProcessorService processorService = env.getService(processorName);
        if(processorService == null){
            showUnknownProcessor(processorName);
            flag = true;
        }
        else{
            Provider provider = processorService.getProvider();

            System.out.printf("Processor Name: %s%n", processorName);
            System.out.printf("Description: %s%n", processorService.getDescription());
            Author[] authors = provider.getAuthors();
            if(authors.length == 1){
                System.out.printf("Author: %s%n", authors[0]);
            }
            else if(authors.length > 1){
                System.out.println("Authors:");
                for(Author author: authors){
                    System.out.printf("    %s%n", author);
                }
            }
            Organization org = provider.getOrganization();
            if(org != null){
                System.out.printf("Organization: %s ", org.getName());
                if(org.getUrl() != null){
                    System.out.printf("<%s>", org.getUrl());
                }
                System.out.println();
            }
            Arguments arguments = processorService.getDefaultArguments();
            if(arguments.getArgumentCount() > 0){
                System.out.println();
                System.out.println("Parameters");
                for(Argument arg: arguments){
                    System.out.printf(
                        "    %s: %s%n        %s%n",
                        arg.getName(), arg.getValue(), arg.getDescription()
                    );
                }
            }
        }
        return flag;
    }

    private void showSimpleHelp(CommandLinePlus commandLine, Environment env, Options options){
        HelpFormatter formatter = new HelpFormatter();
        Package packageObject = getClass().getPackage();
        if(packageObject != null){
            formatter.printHelp(
                String.format(
                    "java tuchinoko-%s.jar [OPTIONS] <targets...>",
                    packageObject.getImplementationVersion()
                ),
                options
            );
        }
        else{
            formatter.printHelp("java tuchinoko.jar [OPTIONS] <targets...>", options);
        }

        showProcessorList(env);
    }

    private void showProcessorList(Environment env){
        if(env.getServiceCount() > 0){
            System.out.println();
            System.out.println("Available Processors");
            for(ProcessorService service: env){
                System.out.printf("    %s: %s%n", service.getProcessorName(), service.getDescription());
            }
        }
    }

    private Options buildOptions(){
        try{
            OptionsBuilderFactory factory = OptionsBuilderFactory.getInstance();
            URL location = getClass().getResource("/resources/options.xml");
            OptionsBuilder builder = factory.createBuilder(location);
            Options options = builder.buildOptions();

            return options;
        }catch(XmlCliConfigurationException ex){
            ex.printStackTrace();
        }catch(DOMException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args){
        new Main(args);
    }
}
